package com.mao.ky.service.rcd;

import com.mao.ky.entity.rcd.dict.Dict;
import com.mao.ky.entity.response.ResponseData;

/**
 * 字典数据管理
 * @author : create by zongx at 2020/11/10 15:44
 */
public interface DictService {

    void dictCache() throws Exception;

    ResponseData<?> dictTree();

    ResponseData<?> provinceTree();

    ResponseData<?> dictType();

    ResponseData<?> dictSave(Dict dict);

    ResponseData<?> dictEdit(Dict dict);

    ResponseData<?> dictDel(String id);

}
