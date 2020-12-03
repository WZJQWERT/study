package com.common.platform.sys.dictmap;

import com.common.platform.base.dict.AbstractDictMap;

public class RoleDict extends AbstractDictMap {
    @Override
    public void init() {
        put("roleId", "角色名称");
        put("sort", "角色排序");
        put("pid", "角色父级");
        put("name", "角色名称");
        put("description", "角色描述");
        put("ids", "角色权限");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("pid", "getSingleRoleName");
        putFieldWrapperMethodName("roleId", "getSingleRoleName");
        putFieldWrapperMethodName("ids", "getMenuNames");
    }
}
