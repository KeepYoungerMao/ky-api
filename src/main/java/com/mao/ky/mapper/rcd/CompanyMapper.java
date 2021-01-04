package com.mao.ky.mapper.rcd;

import com.mao.ky.entity.rcd.company.CompanyDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : create by zongx at 2020/12/4 10:34
 */
@Repository
@Mapper
public interface CompanyMapper {

    List<CompanyDo> getAllCompany();

    CompanyDo getCompanyById(@Param("id") long id);

    void saveCompany(CompanyDo companyDo);

    int updateCompany(CompanyDo companyDo);

    int deleteCompany(@Param("id") long id);

}
