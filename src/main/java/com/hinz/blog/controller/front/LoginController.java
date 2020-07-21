package com.hinz.blog.controller.front;

import com.google.gson.Gson;
import com.hinz.blog.common.util.OkHttpClientUtil;
import com.hinz.blog.config.GitHubProperties;
import com.hinz.blog.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.constraints.NotEmpty;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：quanhz
 * @date ：Created in 2020/7/21 14:31
 */
@Slf4j
@Controller
public class LoginController {
    @Autowired
    private GitHubProperties gitHubProperties;

    @Autowired
    private Gson gson;

    @RequestMapping("/authorize/redirect")
    public ModelAndView authorize(@NotEmpty String code) {

        log.info("授权码code: {}", code);

        /**
         * 重新到前端主页
         */
        String redirectHome = "http://localhost:8080";

        try {
            /**
             * 1、拼装获取accessToken url
             */
            String accessTokenUrl = gitHubProperties.getAccesstokenUrl()
                    .replace("clientId", gitHubProperties.getClientId())
                    .replace("clientSecret", gitHubProperties.getClientSecret())
                    .replace("authorize_code", code);

            /**
             * 返回结果中直接返回token
             */
            String result = OkHttpClientUtil.sendByGetUrl(accessTokenUrl);
            log.info(" 请求 token 结果：{}", result);

            String accessToken = null;
            Pattern p = Pattern.compile("=(\\w+)&");
            Matcher m = p.matcher(result);
            while (m.find()) {
                accessToken = m.group(1);
                log.info("令牌token：{}", m.group(1));
                break;
            }

            /**
             * 成功获取token后，开始请求用户信息
             */
            String userInfoUrl = gitHubProperties.getUserUrl();

            String userResult = OkHttpClientUtil.sendTokenToGitHub(userInfoUrl,accessToken);

            log.info("用户信息：{}", userResult);

            UserInfo userInfo = gson.fromJson(userResult, UserInfo.class);

            redirectHome += "?name=" + userInfo.getName();

        } catch (Exception e) {
            log.error("授权回调异常={}", e);
        }
        return new ModelAndView(new RedirectView(redirectHome));
    }
}
