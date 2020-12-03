package com.common.platform.sys.dictmap;

import com.common.platform.base.dict.AbstractDictMap;

public class DeptDict extends AbstractDictMap {
    @Override
    public void init() {
        put("deptId","部门名称");
        put("sort","部门排序");
        put("pid","父级名称");
        put("simpleName","部门简称");
        put("fullName","部门全称");
        put("description","部门描述");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("deptId","getDeptName");
        putFieldWrapperMethodName("pid","getDeptName");
    }
}
