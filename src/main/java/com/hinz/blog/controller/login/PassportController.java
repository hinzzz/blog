package com.atguigu.gmall.passport.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.UmsMember;
import com.atguigu.gmall.service.UserService;
import com.atguigu.gmall.util.HttpclientUtil;
import com.atguigu.gmall.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PassportController {

    @Reference
    UserService userService;

    @RequestMapping("verify")
    @ResponseBody
    public String verify(String token, String currentIp, HttpServletRequest request){
        //通过jwt校验token真假
        Map<String,String> map = new HashMap<>();
        System.out.println();

        if(StringUtils.isBlank(currentIp)){
            currentIp = request.getHeader("x-forwarded-for");//通过nginx转发的客户端ip
            if(StringUtils.isBlank(currentIp)){
                currentIp = request.getRemoteAddr();//从request中获取ip
                if(StringUtils.isBlank(currentIp)){
                    currentIp = "127.0.0.1";
                }
            }
        }
        Map<String,Object> decode = JwtUtil.decode(token,"2019gmall0227",currentIp);
        if(decode != null){
            map.put("status","success");
            map.put("memberId",(String)decode.get("memberId"));
            map.put("nickname",(String)decode.get("nickname"));
        }else{
            map.put("status","fail");
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping("login")
    @ResponseBody
    public String login(UmsMember umsMember,HttpServletRequest request){
        String token = "";
        //调用用户服务验证用户名和密码
        UmsMember umsMemberLogin = userService.login(umsMember);
        if(umsMemberLogin != null){
            //登陆成功
            //用jwt制作token
            String memberId = umsMemberLogin.getId();
            String nickname = umsMemberLogin.getNickname();
            Map<String,Object> userMap = new HashMap<>();
            userMap.put("memberId",memberId);
            userMap.put("nickname",nickname);
            String ip = request.getHeader("x-forwarded-for");//通过nginx转发的客户端ip
            if(StringUtils.isBlank(ip)){
                ip = request.getRemoteAddr();//从request中获取ip
                if(StringUtils.isBlank(ip)){
                    ip = "127.0.0.1";
                }
            }
            //按照设计的算法对参数进行加密后，生成token
             token = JwtUtil.encode("2019gmall0227", userMap, ip);
            userService.addUserToken(token,memberId);
        }else{
            //登陆失败
            token = "fail";
        }
        return token;
    }

    @RequestMapping("index")
    public String index(String ReturnUrl, ModelMap map){
        map.put("ReturnUrl",ReturnUrl);
        return "index";
    }

    @RequestMapping("vlogin")
    @ResponseBody
    public String vlogin(String code,HttpServletRequest request){
      //  https://api.weibo.com/oauth2/authorize?client_id=YOUR_CLIENT_ID&response_type=code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI
        //授权码换取access_token
        //client_secret = b83f79cee9445d33b2399a93b3ebae1c
        ///client_id = 1742328917
        String s3 = "https://api.weibo.com/oauth2/access_token?";
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("client_id","1742328917");
        paramMap.put("client_secret","b83f79cee9445d33b2399a93b3ebae1c");
        paramMap.put("grant_type","authorization_code");
        paramMap.put("redirect_uri","http://passport.gmall.com:8075:vlogin");
        paramMap.put("code",code);//授权
        String access_token_json = HttpclientUtil.doPost(s3, paramMap);

        Map<String,Object> access_map = JSON.parseObject(access_token_json,Map.class);
        //access_token换取用户信息
       String uid = (String) access_map.get("uid");
       String access_token = (String) access_map.get("access_token");
       String show_user_url = "https://api.weibo.com/2/users/show.json?access_token=" +access_token + "&uid=" + uid;
       String user_json = HttpclientUtil.doGet(show_user_url);
       Map<String,Object> user_map = JSON.parseObject(user_json,Map.class);

       //将用户信息保存数据库，用户类型设置为微博用户
        UmsMember umsMember = new UmsMember();
        umsMember.setSourceType(2);
        umsMember.setAccessCode(code);
        umsMember.setAccessToken(access_token);
        umsMember.setSourceUid((String)user_map.get("idStr"));
        umsMember.setCity((String)user_map.get("location"));
        umsMember.setNickname((String)user_map.get("screen_name"));
        int g= 0;
        String gender = (String)user_map.get("gender");
        if("m".equals(gender)){
            g = 1;
        }
        umsMember.setGender(g);
        UmsMember umsCheck = new UmsMember();
        umsCheck.setSourceUid(umsMember.getSourceUid());

        UmsMember umsMemberCheck = userService.checkOauthUser(umsCheck);
        if(umsMemberCheck == null){
            userService.addOauthUser(umsMember);
        }else{
            umsMember = umsMemberCheck;
        }
        //生成jwt的token，并且重定向到首页，携带token
        String token = null;
        String memberId = umsMember.getId();
        String nickname = umsMember.getNickname();
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("memberId",memberId);
        userMap.put("nickname",nickname);

        String ip = request.getHeader("x-forwarded-for");// 通过nginx转发的客户端ip
        if(StringUtils.isBlank(ip)){
            ip = request.getRemoteAddr();// 从request中获取ip
            if(StringUtils.isBlank(ip)){
                ip = "127.0.0.1";
            }
        }

        // 按照设计的算法对参数进行加密后，生成token
        token = JwtUtil.encode("2019gmall0227", userMap, ip);

        //将token存入redis一份
        userService.addUserToken(token,memberId);

        return "redirect:http://localhost:8073/index?token=" +token;
    }
}
