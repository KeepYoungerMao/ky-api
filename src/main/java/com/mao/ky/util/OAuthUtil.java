package com.mao.ky.util;

import com.mao.ky.entity.sys.Browser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * OAuth2 工具类
 * @author : create by zongx at 2020/10/16 14:13
 */
public class OAuthUtil {

    private static final Logger LOG = LoggerFactory.getLogger(OAuthUtil.class);

    /**
     * 获取当前客户端ClientId
     */
    public static Browser getBrowser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
            String username = oAuth2Authentication.getUserAuthentication().getName();
            String clientId = oAuth2Authentication.getOAuth2Request().getClientId();
            return new Browser(clientId, username);
        } catch (Exception e) {
            LOG.warn("current request is non-user-request.");
            return null;
        }
    }

}
