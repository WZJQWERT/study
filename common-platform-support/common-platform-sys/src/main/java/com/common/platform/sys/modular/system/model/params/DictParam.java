package com.common.platform.sys.modular.system.model.params;

import com.common.platform.sys.base.pojo.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;

@Data
public class DictParam implements Serializable, BaseValidatingParam {

    /**
     * 字典id
     */
    private Long dictId;

    /**
     * 所属字典类型的id
     */
    private Long dictTypeId;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 上级代码id
     */
    private Long parentId;

    /**
     * 状态（字典）
     */
    private String status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 字典的描述
     */
    private String description;

    /**
     * 查询条件
     */
    private String condition;

    @Override
    public String checkParam() {
        return null;
    }
}
