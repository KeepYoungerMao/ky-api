package com.mao.ky.mapper.rcd;

import com.mao.ky.entity.rcd.dict.DictDo;
import com.mao.ky.entity.rcd.dict.DictType;
import com.mao.ky.entity.rcd.dict.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : create by zongx at 2020/11/10 15:51
 */
@Repository
@Mapper
public interface DictMapper {

    List<Province> getAllProvince();

    List<DictType> getAllDictType();

    List<DictDo> getAllDict();

    void saveDict(DictDo dictDo);

    int updateDict(DictDo dictDo);

    int delDict(@Param("id") long id);

}
