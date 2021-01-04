package com.mao.ky.config;

import com.mao.ky.service.rcd.CompanyService;
import com.mao.ky.service.rcd.DeptService;
import com.mao.ky.service.rcd.DictService;
import com.mao.ky.util.SU;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 程序启动后初始化加载程序
 * @author : create by zongx at 2020/11/10 15:48
 */
@Component
public class ApplicationInit implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ApplicationInit.class);

    private DictService dictService;
    private CompanyService companyService;
    private DeptService deptService;

    @Autowired
    public void setDictService(DictService dictService) {
        this.dictService = dictService;
    }
    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }
    @Autowired
    public void setDeptService(DeptService deptService) {
        this.deptService = deptService;
    }

    @Override
    public void run(String... args) throws Exception {

        //字典数据缓存
        dictInit();

        //公司部门数据缓存
        companyCache();

        //打印加载成功标识
        SU.pfSuccess();

    }

    private void dictInit() throws Exception {
        dictService.dictCache();
        log.info("dict data cached success...");
    }

    private void companyCache() throws Exception {
        companyService.companyCache();
        deptService.deptCache();
        log.info("company and department data cached success...");
    }

}
