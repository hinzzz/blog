package com.hinz.blog.controller.login;

import com.google.gson.Gson;
import com.hinz.blog.common.constant.CodeEnum;
import com.hinz.blog.common.constant.Const;
import com.hinz.blog.common.util.Result;
import com.hinz.blog.model.BlogMember;
import com.hinz.blog.service.BlogUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private BlogUserService blogUserService;

    @PostMapping(value = "loginblog")
    @ResponseBody
    public Result login(@RequestBody BlogMember blogMember, HttpServletRequest request, HttpSession session){
        String token = "";
        //调用用户服务验证用户名和密码
        Result result = blogUserService.login(blogMember);
        if(result.getCode()== CodeEnum.SUCCESS.getValue()){
            //登录成功，将用户信息保存至session
            session.setAttribute(Const.USER_SESSION_KEY,result.getData());
            result.setData(session.getId());
        }
        return result;
    }

    @PostMapping(value = "vlogin")
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
        String access_token_json ="";/// HttpclientUtil.doPost(s3, paramMap);

       /// Map<String,Object> access_map = JSON.parseObject(access_token_json,Map.class);
        Gson gson = new Gson();
        Map<String,Object>  access_map = gson.fromJson(access_token_json, Map.class);

        //access_token换取用户信息
       String uid = (String) access_map.get("uid");
       String access_token = (String) access_map.get("access_token");
       String show_user_url = "https://api.weibo.com/2/users/show.json?access_token=" +access_token + "&uid=" + uid;
       String user_json = "";//HttpclientUtil.doGet(show_user_url);
     ///  Map<String,Object> user_map = JSON.parseObject(user_json,Map.class);
        Map<String,Object> user_map = gson.fromJson(user_json,Map.class);

       //将用户信息保存数据库，用户类型设置为微博用户
        BlogMember blogMember = new BlogMember();
        blogMember.setSourceType(2);
        blogMember.setAccessCode(code);
        blogMember.setAccessToken(access_token);
        blogMember.setSourceUid((String)user_map.get("idStr"));
        blogMember.setCity((String)user_map.get("location"));
        blogMember.setNickname((String)user_map.get("screen_name"));
        int g= 0;
        String gender = (String)user_map.get("gender");
        if("m".equals(gender)){
            g = 1;
        }
        blogMember.setGender(g);
        BlogMember umsCheck = new BlogMember();
        umsCheck.setSourceUid(blogMember.getSourceUid());

      //  blogMember blogMemberCheck = userService.checkOauthUser(umsCheck);
       // if(blogMemberCheck == null){
        //    userService.addOauthUser(blogMember);
      //  }else{
         //   blogMember = blogMemberCheck;
       // }
        //生成jwt的token，并且重定向到首页，携带token
        String token = null;
        String memberId = blogMember.getId();
        String nickname = blogMember.getNickname();
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
       /// token = JwtUtil.encode("2019gmall0227", userMap, ip);

        //将token存入redis一份
      ///  userService.addUserToken(token,memberId);

        return "redirect:http://localhost:8073/index?token=" +token;
    }
}
