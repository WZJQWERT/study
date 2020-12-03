package com.common.platform.base.dict;

import java.util.HashMap;

public abstract class AbstractDictMap {

    protected HashMap<String,String> dictory = new HashMap<>();
    protected HashMap<String,String> fieldWarpperDictory= new HashMap<>();

    /**
     * 初始化字段英文名称和中文名称的对应字典
     */
    public abstract void init();

    /**
     * 初始化需要被包装的字段（例如：性别 1男，2女，需要被包装为汉字）
     */
    protected abstract void initBeWrapped();

    public String get(String key){
        return this.dictory.get(key);
    }

    public void put(String key,String value){
        this.dictory.put(key,value);
    }

    public String getFieldWarpperMethodName(String key){
        return this.fieldWarpperDictory.get(key);
    }

    public void putFieldWrapperMethodName(String key,String methodName){
        this.fieldWarpperDictory.put(key,methodName);
    }

    public AbstractDictMap(){
        put("id","主键Id");
        init();
        initBeWrapped();
    }
}
