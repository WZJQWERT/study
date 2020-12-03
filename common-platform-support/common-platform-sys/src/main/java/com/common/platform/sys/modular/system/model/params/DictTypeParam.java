package com.common.platform.sys.modular.system.model.params;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.common.platform.sys.base.pojo.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;

@Data
public class DictTypeParam implements Serializable, BaseValidatingParam {

    /**
     * 字典类型id
     */
    private Long dictTypeId;

    /**
     * 字典类型编码
     */
    private String code;

    /**
     * 字典类型名称
     */
    private String name;

    /**
     * 字典描述
     */
    private String description;

    /**
     * 是否是系统字典，Y-是，N-否
     */
    private String systemFlag;

    /**
     * 状态(字典)
     */
    private String status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 查询条件
     */
    private String condition;

    @Override
    public String checkParam() {
        return null;
    }
}
