package com.mao.ky.service.rcd.impl;

import com.mao.ky.config.cache.CDSCache;
import com.mao.ky.entity.rcd.company.Company;
import com.mao.ky.entity.rcd.company.CompanyCache;
import com.mao.ky.entity.rcd.company.CompanyDo;
import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.mapper.rcd.CompanyMapper;
import com.mao.ky.service.BaseService;
import com.mao.ky.service.rcd.CompanyService;
import com.mao.ky.util.SU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : create by zongx at 2020/12/3 17:45
 */
@Service
public class CompanyServiceImpl extends BaseService implements CompanyService {

    private CompanyMapper companyMapper;

    @Autowired
    public void setCompanyMapper(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    @Override
    public void companyCache() {
        List<CompanyDo> list = companyMapper.getAllCompany();
        CDSCache.cacheCompany(list);
    }

    @Override
    public ResponseData<?> companySrc(String id) {
        Long tid = SU.parseId(id);
        if (!SU.isZS(tid))
            return paramErr("invalid company id");
        CompanyDo companyDo = companyMapper.getCompanyById(tid);
        if (null == companyDo || null == companyDo.getId())
            return paramErr("invalid company id");
        return ok(companyDo.toVo());
    }

    @Override
    public ResponseData<?> companyList() {
        List<CompanyCache> list = CDSCache.getCompany();
        if (list.size() <= 0)
            return ok(list);
        List<Map<String, Object>> result = new ArrayList<>();
        list.forEach(cache -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", String.valueOf(cache.getId()));
            map.put("name", cache.getName());
            map.put("code", cache.getCode());
            result.add(map);
        });
        return ok(result);
    }

    @Transactional
    @Override
    public ResponseData<?> companySave(Company company) {
        CompanyDo companyDo = company.toDo();
        if (CDSCache.existCompany(companyDo.getCode()))
            return paramErr("company code has used");
        companyDo.setId(maoFlake.nextId());
        companyMapper.saveCompany(companyDo);
        CDSCache.addCompany(companyDo);
        return ok("success to save");
    }

    @Transactional
    @Override
    public ResponseData<?> companyEdit(Company company) {
        Long tid = SU.parseId(company.getId());
        if (!SU.isZS(tid))
            return paramErr("invalid company id");
        CompanyDo companyDo = company.toDo();
        companyDo.setId(tid);
        if (CDSCache.existCompany(companyDo.getCode(), companyDo.getId()))
            return paramErr("company code has used");
        int count = companyMapper.updateCompany(companyDo);
        if (count <= 0)
            return dataAccessErr("failed to update, invalid company id");
        CDSCache.editCompany(companyDo);
        return ok("success to update");
    }

    @Transactional
    @Override
    public ResponseData<?> companyDel(String id) {
        Long tid = SU.parseId(id);
        if (!SU.isZS(tid))
            return paramErr("invalid company id");
        int count = companyMapper.deleteCompany(tid);
        if (count <= 0)
            return dataAccessErr("failed to delete company.");
        CDSCache.removeCompany(tid);
        return ok("success to delete");
    }

}
