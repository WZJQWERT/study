package com.common.platform.base.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    //要查询第几页
    private Integer page = 1;
    //每页需要显示多少条记录
    private Integer pageSize = 20;
    //总页数
    private Integer totalPage = 0;
    //总记录数
    private Long totalRows = 0L;
    //当前页的数据集合
    private List<T> rows;

    public PageResult(Page<T> page){
        this.setTotalRows(page.getTotal());
        this.setTotalPage((int)page.getPages());
        this.setRows(page.getRecords());
        this.setPage((int)page.getCurrent());
        this.setPageSize((int)page.getSize());
    }
}
