package com.common.platform.base.page;

import lombok.Data;

import java.util.List;

@Data
public class LayuiPageInfo {

    private Integer code = 0;
    private String msg = "请求成功";
    private List data;
    private long count;
}
