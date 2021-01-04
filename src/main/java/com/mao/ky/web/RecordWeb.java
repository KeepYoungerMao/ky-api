package com.mao.ky.web;

import com.mao.ky.entity.log.req.Log;
import com.mao.ky.entity.log.req.ReqData;
import com.mao.ky.entity.rcd.company.Company;
import com.mao.ky.entity.rcd.dept.Dept;
import com.mao.ky.entity.rcd.dict.Dict;
import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.service.rcd.CompanyService;
import com.mao.ky.service.rcd.DeptService;
import com.mao.ky.service.rcd.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author : create by zongx at 2020/11/25 16:09
 */
@RestController
@RequestMapping("api/rcd")
public class RecordWeb {

    private DictService dictService;
    private DeptService deptService;
    private CompanyService companyService;

    @Autowired
    public void setDictService(DictService dictService) {
        this.dictService = dictService;
    }

    @Autowired
    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Log(ReqData.DICT)
    @GetMapping("tree/dict")
    public ResponseData<?> dictTree() {
        return dictService.dictTree();
    }

    @Log(ReqData.DICT)
    @GetMapping("tree/province")
    public ResponseData<?> provinceTree() {
        return dictService.provinceTree();
    }

    @Log(ReqData.DICT)
    @GetMapping("dict_type")
    public ResponseData<?> dictType() {
        return dictService.dictType();
    }

    @Log(ReqData.DICT)
    @PutMapping("dict")
    public ResponseData<?> dictSave(@Validated @RequestBody Dict dict) {
        return dictService.dictSave(dict);
    }

    @Log(ReqData.DICT)
    @PostMapping("dict")
    public ResponseData<?> dictEdit(@Validated @RequestBody Dict dict) {
        return dictService.dictEdit(dict);
    }

    @Log(ReqData.DICT)
    @DeleteMapping("dict/{id}")
    public ResponseData<?> dictDel(@PathVariable("id") String id) {
        return dictService.dictDel(id);
    }

    @Log(ReqData.DEPT)
    @GetMapping("department/{id}")
    public ResponseData<?> deptSrc(@PathVariable("id") String id) {
        return deptService.deptSrc(id);
    }

    @Log(ReqData.DEPT)
    @GetMapping("departments")
    public ResponseData<?> deptList() {
        return deptService.deptList();
    }

    @Log(ReqData.DEPT)
    @PutMapping("department")
    public ResponseData<?> deptSave(@Validated @RequestBody Dept dept) {
        return deptService.deptSave(dept);
    }

    @Log(ReqData.DEPT)
    @PostMapping("department")
    public ResponseData<?> deptEdit(@Validated @RequestBody Dept dept) {
        return deptService.deptEdit(dept);
    }

    @Log(ReqData.DEPT)
    @DeleteMapping("department/{id}")
    public ResponseData<?> deptDel(@PathVariable("id") String id) {
        return deptService.deptDel(id);
    }

    @Log(ReqData.COMPANY)
    @GetMapping("company/{id}")
    public ResponseData<?> companySrc(@PathVariable("id") String id) {
        return companyService.companySrc(id);
    }

    @Log(ReqData.COMPANY)
    @GetMapping("companies")
    public ResponseData<?> companyList() {
        return companyService.companyList();
    }

    @Log(ReqData.COMPANY)
    @PutMapping("company")
    public ResponseData<?> companySave(@Validated @RequestBody Company company) {
        return companyService.companySave(company);
    }

    @Log(ReqData.COMPANY)
    @PostMapping("company")
    public ResponseData<?> companyEdit(@Validated @RequestBody Company company) {
        return companyService.companyEdit(company);
    }

    @Log(ReqData.COMPANY)
    @DeleteMapping("company/{id}")
    public ResponseData<?> companyDel(@PathVariable("id") String id) {
        return companyService.companyDel(id);
    }

}
