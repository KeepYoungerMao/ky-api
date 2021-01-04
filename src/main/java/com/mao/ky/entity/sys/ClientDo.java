package com.mao.ky.entity.sys;

import com.mao.ky.entity.Sign;
import com.mao.ky.util.SU;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.io.Serializable;
import java.util.*;

/**
 * 客户端数据
 * @author : create by zongx at 2020/11/13 14:01
 */
@Getter
@Setter
public class ClientDo extends Sign implements ClientDetails, Serializable {

    private Long id;        //client_id
    private String name;
    private String company;
    private String email;
    private String secret;      //client_secret
    private String resources;   //resource_ids
    private Boolean scoped;
    private String scope;
    private String grant_type;
    private String redirect_uri;
    private Integer token_expire;
    private Integer refresh_expire;
    private Boolean auto_approve;

    @Override
    public String getClientId() {
        return String.valueOf(this.id);
    }

    @Override
    public Set<String> getResourceIds() {
        return SU.unJoin(this.resources);
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return this.secret;
    }

    @Override
    public boolean isScoped() {
        return this.scoped;
    }

    @Override
    public Set<String> getScope() {
        return SU.unJoin(this.scope);
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return SU.unJoin(this.grant_type);
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        Set<String> set = new HashSet<>();
        set.add(this.redirect_uri);
        return set;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.token_expire;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return this.refresh_expire;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return this.auto_approve;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", this.name);
        map.put("company", this.company);
        map.put("email", this.email);
        return map;
    }

}
