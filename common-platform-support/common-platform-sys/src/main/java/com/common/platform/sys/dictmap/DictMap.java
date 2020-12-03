package com.common.platform.sys.dictmap;

import com.common.platform.base.dict.AbstractDictMap;

public class DictMap extends AbstractDictMap {

    @Override
    public void init() {
        put("dictId","字典名称");
        put("name","字典名称");
        put("code","字典编码");
        put("description","字典描述");
        put("sort","字典排序");
    }

    @Override
    protected void initBeWrapped() {

    }
}
