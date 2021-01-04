package com.mao.ky.service.rcd;

import com.mao.ky.entity.rcd.company.Company;
import com.mao.ky.entity.response.ResponseData;

/**
 * @author : create by zongx at 2020/12/3 17:44
 */
public interface CompanyService {

    void companyCache() throws Exception;

    ResponseData<?> companySrc(String id);

    ResponseData<?> companyList();

    ResponseData<?> companySave(Company company);

    ResponseData<?> companyEdit(Company company);

    ResponseData<?> companyDel(String id);

}
