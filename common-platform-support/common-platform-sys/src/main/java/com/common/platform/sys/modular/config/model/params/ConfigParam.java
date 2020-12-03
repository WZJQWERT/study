package com.common.platform.sys.modular.config.model.params;

import com.common.platform.sys.base.pojo.BaseValidatingParam;
import lombok.Data;

import java.io.Serializable;

@Data
public class ConfigParam implements Serializable, BaseValidatingParam {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 属性编码标识
     */
    private String code;

    /**
     * 是否是字典中的值
     */
    private String dictFlag;

    /**
     * 字典中的值
     */
    private String dictValue;

    /**
     * 字典类型的编码
     */
    private Long dictTypeId;

    /**
     * 属性值，如果是字典中的类型，则为dict的code
     */
    private String value;

    /**
     * 备注
     */
    private String remark;

    @Override
    public String checkParam() {
        return null;
    }
}
