package com.mao.ky.service.rcd.impl;

import com.mao.ky.config.cache.DictCache;
import com.mao.ky.entity.rcd.dict.Dict;
import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.entity.rcd.dict.DictDo;
import com.mao.ky.entity.rcd.dict.DictType;
import com.mao.ky.entity.rcd.dict.Province;
import com.mao.ky.mapper.rcd.DictMapper;
import com.mao.ky.service.BaseService;
import com.mao.ky.service.rcd.DictService;
import com.mao.ky.util.SU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典数据管理
 * @author : create by zongx at 2020/11/10 15:44
 */
@Service
public class DictServiceImpl extends BaseService implements DictService {

    private DictMapper dictMapper;

    @Autowired
    public void setDictMapper(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public void dictCache() throws Exception {
        List<Province> province = dictMapper.getAllProvince();
        if (province.size() <= 0)
            throw new Exception("province dict is empty...");
        DictCache.cacheProvince(province);
        List<DictType> dictType = dictMapper.getAllDictType();
        if (dictType.size() <= 0)
            throw new Exception("dict type is empty...");
        List<DictDo> dict = dictMapper.getAllDict();
        if (dict.size() <= 0)
            throw new Exception("dict is empty...");
        DictCache.cacheDict(dictType, dict);
    }

    @Override
    public ResponseData<?> dictTree() {
        return ok(DictCache.getDict());
    }

    @Override
    public ResponseData<?> provinceTree() {
        return ok(DictCache.getProvinceTrees());
    }

    @Override
    public ResponseData<?> dictType() {
        return ok(DictCache.getDictType());
    }

    @Transactional
    @Override
    public ResponseData<?> dictSave(Dict dict) {
        DictDo dictDo = dict.toDo();
        if (null == dictDo.getType())
            return paramErr("invalid dict type");
        dictDo.setId(maoFlake.nextId());
        dictMapper.saveDict(dictDo);
        DictCache.addDict(dictDo.toVo());
        return ok("success to save");
    }

    @Transactional
    @Override
    public ResponseData<?> dictEdit(Dict dict) {
        Long id = SU.parseId(dict.getId());
        if (!SU.isZS(id))
            return paramErr("invalid dict id");
        DictDo dictDo = dict.toDo();
        if (null == dictDo.getType())
            return paramErr("invalid dict type");
        dictDo.setId(id);
        int count = dictMapper.updateDict(dictDo);
        if (count <= 0)
            return dataAccessErr("failed to update");
        DictCache.editDict(dictDo.toVo());
        return ok("success to update");
    }

    @Transactional
    @Override
    public ResponseData<?> dictDel(String id) {
        Long tid = SU.parseId(id);
        if (!SU.isZS(tid))
            return paramErr("invalid dict id");
        int count = dictMapper.delDict(tid);
        if (count <= 0)
            return dataAccessErr("failed to delete");
        DictCache.delDict(id);
        return ok("success to delete");
    }

}
