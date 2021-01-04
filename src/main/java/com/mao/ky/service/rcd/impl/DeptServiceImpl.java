package com.mao.ky.service.rcd.impl;

import com.mao.ky.config.cache.CDSCache;
import com.mao.ky.entity.rcd.dept.Dept;
import com.mao.ky.entity.rcd.dept.DeptCache;
import com.mao.ky.entity.rcd.dept.DeptDo;
import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.mapper.rcd.DeptMapper;
import com.mao.ky.service.BaseService;
import com.mao.ky.service.rcd.DeptService;
import com.mao.ky.util.SU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : create by zongx at 2020/12/3 17:44
 */
@Service
public class DeptServiceImpl extends BaseService implements DeptService {

    private DeptMapper deptMapper;

    @Autowired
    public void setDeptMapper(DeptMapper deptMapper) {
        this.deptMapper = deptMapper;
    }

    @Override
    public void deptCache() {
        List<DeptDo> list = deptMapper.getAllDept();
        CDSCache.cacheDept(list);
    }

    @Override
    public ResponseData<?> deptSrc(String id) {
        Long tid = SU.parseId(id);
        if (!SU.isZS(tid))
            return paramErr("invalid department id");
        DeptDo deptDo = deptMapper.getDeptById(tid);
        if (null == deptDo || null == deptDo.getId())
            return paramErr("invalid department id");
        return ok(deptDo.toVo());
    }

    @Override
    public ResponseData<?> deptList() {
        List<DeptCache> list = CDSCache.getDept();
        if (list.size() <= 0)
            return ok(list);
        List<Map<String, Object>> result = new ArrayList<>();
        list.forEach(cache -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", String.valueOf(cache.getId()));
            map.put("pid", String.valueOf(cache.getPid()));
            map.put("cid", String.valueOf(cache.getCid()));
            map.put("name", cache.getName());
            map.put("code", cache.getCode());
            result.add(map);
        });
        return ok(result);
    }

    @Transactional
    @Override
    public ResponseData<?> deptSave(Dept dept) {
        DeptDo deptDo = dept.toDo();
        Long pid = SU.parseId(dept.getPid());
        if ( null == pid || pid < 0 || (pid != 0 && CDSCache.unExistDept(pid)) )
            return paramErr("invalid parent department id");
        deptDo.setPid(pid);
        Long cid = SU.parseId(dept.getCid());
        if (!SU.isZS(cid) || CDSCache.unExistCompany(cid))
            return paramErr("invalid company id");
        deptDo.setCid(cid);
        if (CDSCache.existDept(deptDo.getCode()))
            return paramErr("department code has used");
        deptDo.setId(maoFlake.nextId());
        deptMapper.saveDept(deptDo);
        CDSCache.addDept(deptDo);
        return ok("success to save");
    }

    @Transactional
    @Override
    public ResponseData<?> deptEdit(Dept dept) {
        DeptDo deptDo = dept.toDo();
        Long tid = SU.parseId(dept.getId());
        if (!SU.isZS(tid))
            return paramErr("invalid department id");
        deptDo.setId(tid);
        Long cid = SU.parseId(dept.getCid());
        if (!SU.isZS(cid) || CDSCache.unExistCompany(cid))
            return paramErr("invalid company id");
        deptDo.setCid(cid);
        Long pid = SU.parseId(dept.getPid());
        if ( null == pid || pid < 0 || (pid != 0 && CDSCache.unExistDept(pid)) )
            return paramErr("invalid parent department id");
        deptDo.setPid(pid);
        if (CDSCache.existDept(deptDo.getCode(), deptDo.getId()))
            return paramErr("department code has used");
        int count = deptMapper.updateDept(deptDo);
        if (count <= 0)
            return dataAccessErr("failed to update, invalid department id");
        CDSCache.editDept(deptDo);
        return ok("success to update");
    }

    @Transactional
    @Override
    public ResponseData<?> deptDel(String id) {
        Long tid = SU.parseId(id);
        if (!SU.isZS(tid))
            return paramErr("invalid department id");
        int count = deptMapper.deleteDept(tid);
        if (count <= 0)
            return dataAccessErr("failed to delete department.");
        CDSCache.removeDept(tid);
        return ok("success to delete");
    }

}
