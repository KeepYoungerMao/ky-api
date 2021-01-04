package com.mao.ky.service.auth;

import com.mao.ky.mapper.sys.ClientMapper;
import com.mao.ky.util.SU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * 客户端数据查询
 * OAuth2
 * @author : create by zongx at 2020/11/13 13:59
 */
@Service
public class JdbcClientDetailsService implements ClientDetailsService {

    private ClientMapper clientMapper;

    @Autowired
    public void setClientMapper(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientDetails loadClientByClientId(String client) throws ClientRegistrationException {
        Long id = SU.parseId(client);
        if (!SU.isZS(id))
            return null;
        return clientMapper.getClientById(id);
    }

}
