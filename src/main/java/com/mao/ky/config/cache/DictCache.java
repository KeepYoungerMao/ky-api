package com.mao.ky.config.cache;

import com.mao.ky.entity.rcd.dict.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典缓存
 * @author : create by zongx at 2020/11/10 14:57
 */
public class DictCache {

    /**
     * 对象锁
     */
    private static final Object lock = new Object();

    /**
     * 省市区缓存数据
     */
    private static final List<ProvinceTree> PROVINCE_TREES = new ArrayList<>();

    /**
     * 字典类型缓存
     */
    private static final List<DictType> DICT_TYPE = new ArrayList<>();

    /**
     * 字典数据缓存
     */
    private static final Map<String, List<DictVo>> DICT = new HashMap<>();

    /**
     * 添加省市区数据进入缓存，组成三级联动数据
     * @param list 源数据
     */
    public static void cacheProvince(List<Province> list) {
        synchronized (lock) {
            addCacheProvince(0L, list, PROVINCE_TREES);
        }
    }

    /**
     * 递归添加省市区缓存数据
     */
    private static void addCacheProvince(Long pid, List<Province> list, List<ProvinceTree> result) {
        for (Province p : list) {
            if (p.getPid().equals(pid)) {
                ProvinceTree child = new ProvinceTree(p.getId(), p.getCode(), p.getName());
                result.add(child);
                addCacheProvince(p.getId(), list,child.getChild());
            }
        }
    }

    /**
     * 获取省市区缓存
     */
    public static List<ProvinceTree> getProvinceTrees() {
        synchronized (lock) {
            return PROVINCE_TREES;
        }
    }

    /**
     * 添加字典类型数据和字典数据进入缓存
     */
    public static void cacheDict(List<DictType> types, List<DictDo> dict) {
        synchronized (lock) {
            DICT_TYPE.addAll(types);
            for (DictType type : types) {
                String code = type.getCode();
                List<DictVo> nodes = new ArrayList<>();
                for (DictDo d : dict) {
                    if (d.getType().equals(type.getId())) {
                        nodes.add(d.toVo());
                    }
                }
                DICT.put(code, nodes);
            }
        }
    }

    /**
     * 获取字典缓存
     */
    public static Map<String, List<DictVo>> getDict() {
        synchronized (lock) {
            return DICT;
        }
    }

    /**
     * 获取字典类型缓存
     */
    public static List<DictType> getDictType() {
        synchronized (lock) {
            return DICT_TYPE;
        }
    }

    /**
     * 根据id获取字典类型
     */
    public static DictType getDictType(String id) {
        synchronized (lock) {
            for (DictType type : DICT_TYPE) {
                if (id.equals(String.valueOf(type.getId())))
                    return type;
            }
            return null;
        }
    }

    /**
     * 增加字典
     */
    public static void addDict(DictVo dictVo) {
        DictType dictType = getDictType(dictVo.getType());
        if (null != dictType) {
            synchronized (lock) {
                DICT.get(dictType.getCode()).add(dictVo);
            }
        }
    }

    /**
     * 修改字典
     */
    public static void editDict(DictVo dictVo) {
        DictType dictType = getDictType(dictVo.getType());
        if (null != dictType) {
            synchronized (lock) {
                List<DictVo> list = DICT.get(dictType.getCode());
                for (DictVo vo : list) {
                    if (vo.getId().equals(dictVo.getId())) {
                        vo.setName(dictVo.getName());
                        break;
                    }
                }
            }
        }
    }

    /**
     * 删除字典
     */
    public static void delDict(String id) {
        synchronized (lock) {
            for (Map.Entry<String, List<DictVo>> entry : DICT.entrySet()) {
                boolean del = entry.getValue().removeIf(dictVo -> dictVo.getId().equals(id));
                if (del)
                    break;
            }
        }
    }

}
