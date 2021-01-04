package com.mao.ky.config.cache;

import com.mao.ky.entity.rcd.company.CompanyCache;
import com.mao.ky.entity.rcd.company.CompanyDo;
import com.mao.ky.entity.rcd.dept.DeptCache;
import com.mao.ky.entity.rcd.dept.DeptDo;
import com.mao.ky.util.SU;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司-部门-职员 树缓存
 * @author : create by zongx at 2020/11/10 14:20
 */
public class CDSCache {

    private static final Object lock = new Object();

    private static final List<DeptCache> DEPT_CACHES = new ArrayList<>();

    private static final List<CompanyCache> COMPANY_CACHES = new ArrayList<>();

    public static void cacheCompany(List<CompanyDo> list) {
        synchronized (lock) {
            list.forEach(companyDo -> COMPANY_CACHES.add(companyDo.toCache()));
        }
    }

    public static void addCompany(CompanyDo companyDo) {
        synchronized (lock) {
            COMPANY_CACHES.add(companyDo.toCache());
        }
    }

    public static void editCompany(CompanyDo companyDo) {
        synchronized (lock) {
            for (CompanyCache cache : COMPANY_CACHES) {
                if (cache.getId() == companyDo.getId()) {
                    cache.setName(companyDo.getName());
                    break;
                }
            }
        }
    }

    public static void removeCompany(long id) {
        synchronized (lock) {
            COMPANY_CACHES.removeIf(cache -> cache.getId() == id);
        }
    }

    public static boolean existCompany(String code) {
        if (SU.isEmpty(code))
            return false;
        synchronized (lock) {
            for (CompanyCache cache : COMPANY_CACHES) {
                if (cache.getCode().equals(code))
                    return true;
            }
            return false;
        }
    }

    public static boolean existCompany(String code, long id) {
        if (SU.isEmpty(code))
            return false;
        synchronized (lock) {
            for (CompanyCache cache : COMPANY_CACHES) {
                if (cache.getId() != id && cache.getCode().equals(code))
                    return true;
            }
            return false;
        }
    }

    public static boolean unExistCompany(long id) {
        synchronized (lock) {
            for (CompanyCache cache : COMPANY_CACHES) {
                if (cache.getId() == id)
                    return false;
            }
            return true;
        }
    }

    public static List<CompanyCache> getCompany() {
        synchronized (lock) {
            return COMPANY_CACHES;
        }
    }

    public static void cacheDept(List<DeptDo> list) {
        synchronized (lock) {
            list.forEach(dept -> DEPT_CACHES.add(dept.toCache()));
        }
    }

    public static void addDept(DeptDo deptDo) {
        synchronized (lock) {
            DEPT_CACHES.add(deptDo.toCache());
        }
    }

    public static void editDept(DeptDo deptDo) {
        synchronized (lock) {
            for (DeptCache deptCache : DEPT_CACHES) {
                if (deptCache.getId() == deptDo.getId()) {
                    deptCache.setName(deptDo.getName());
                    break;
                }
            }
        }
    }

    public static void removeDept(long id) {
        synchronized (lock) {
            DEPT_CACHES.removeIf(deptCache -> id == deptCache.getId());
        }
    }

    public static boolean existDept(String code) {
        if (SU.isEmpty(code))
            return false;
        synchronized (lock) {
            for (DeptCache deptCache : DEPT_CACHES) {
                if (deptCache.getCode().equals(code))
                    return true;
            }
            return false;
        }
    }

    public static boolean existDept(String code, long id) {
        if (SU.isEmpty(code))
            return false;
        synchronized (lock) {
            for (DeptCache cache : DEPT_CACHES) {
                if (cache.getId() != id && cache.getCode().equals(code))
                    return true;
            }
            return false;
        }
    }

    public static boolean unExistDept(long id) {
        synchronized (lock) {
            for (DeptCache cache : DEPT_CACHES) {
                if (cache.getId() == id)
                    return false;
            }
            return true;
        }
    }

    public static List<DeptCache> getDept() {
        synchronized (lock) {
            return DEPT_CACHES;
        }
    }

}
