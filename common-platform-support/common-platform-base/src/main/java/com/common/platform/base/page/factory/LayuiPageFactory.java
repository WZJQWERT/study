package com.common.platform.base.page.factory;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.common.platform.base.config.context.HttpContext;
import com.common.platform.base.page.LayuiPageInfo;
import com.common.platform.base.utils.CoreUtil;

import javax.servlet.http.HttpServletRequest;

public class LayuiPageFactory {

    public static Page defaultPage(){
        HttpServletRequest request = HttpContext.getRequest();

        int limit = 20;
        int page = 1;

        //每页要显示多少条数据
        String limitString = request.getParameter("limit");
        if(CoreUtil.isNotEmpty(limitString)){
            limit = Integer.parseInt(limitString);
        }

        //当前查询的第几页
        String pageString = request.getParameter("page");
        if(CoreUtil.isNotEmpty(pageString)){
            page = Integer.parseInt(pageString);
        }

        return new Page(page,limit);
    }

    public static LayuiPageInfo createPageInfo(IPage page){
        LayuiPageInfo layuiPageInfo = new LayuiPageInfo();
        layuiPageInfo.setCount(page.getTotal());
        layuiPageInfo.setData(page.getRecords());
        return layuiPageInfo;
    }
}
