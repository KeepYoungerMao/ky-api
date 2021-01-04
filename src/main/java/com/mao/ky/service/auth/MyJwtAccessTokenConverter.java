package com.mao.ky.service.auth;

import com.mao.ky.entity.sys.user.UserDo;
import com.mao.ky.util.SU;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 实现 JwtAccessTokenConverter token生成器
 * 本类中不做token生成，只是在生成前检查一下用户提交的client_id和数据库中保存的client_id是否一致，
 * 如果不一致，则不允许登录，不发放token。
 * @author : create by zongx at 2020/10/15 17:38
 */
public class MyJwtAccessTokenConverter extends JwtAccessTokenConverter {

    @SneakyThrows
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (null != authentication) {
            //获取
            String clientId = authentication.getOAuth2Request().getClientId();
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDo) {
                UserDo user = (UserDo) principal;
                String realClientId = String.valueOf(user.getClient());
                if (SU.isNotEmpty(clientId) && SU.isNotEmpty(realClientId)) {
                    if (!clientId.equals(realClientId)) {
                        throw new Exception("Failed to login. illegal browser.");
                    }
                }
            }
        }
        return super.enhance(accessToken, authentication);
    }

}
