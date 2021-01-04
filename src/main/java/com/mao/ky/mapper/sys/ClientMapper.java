package com.mao.ky.mapper.sys;

import com.mao.ky.entity.sys.ClientDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : create by zongx at 2020/11/13 15:23
 */
@Repository
@Mapper
public interface ClientMapper {

    ClientDo getClientById(@Param("id") Long id);

    List<ClientDo> getClients();

}
