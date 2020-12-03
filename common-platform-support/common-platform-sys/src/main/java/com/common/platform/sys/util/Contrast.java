package com.common.platform.sys.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.common.platform.base.dict.AbstractDictMap;
import com.common.platform.sys.factory.DictFieldWrapperFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

public class Contrast {

    public static final String separator = ";;;";

    /**
     * 比较两个对象，并返回不一致的信息
     */
    public static String contrastObj(Object obj1,Object obj2){
        String str = "";
        try{
            Class clazz = obj1.getClass();
            Field[] fields = obj1.getClass().getDeclaredFields();
            int i = 1;
            for (Field field : fields){
                if("serialVersionUID".equals(field.getName())){
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);
                Method getMethod = pd.getReadMethod();

                Object o1 = getMethod.invoke(obj1);
                Object o2 = getMethod.invoke(obj2);
                if(o1==null || o2==null){
                    continue;
                }
                if(o1 instanceof Date){
                    o1 = DateUtil.formatDate((Date) o1);
                }
                if(!o1.toString().equals(o2.toString())){
                    if(i!=1){
                        str += separator;
                    }
                    str += "字段名称" + field.getName() + ",旧值："+ o1 + ",新值："+o2;
                    i++;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }

    public static String contrastObj(Class dictClass, String key, Object obj1, Map<String,String> obj2)
            throws IllegalAccessException, InstantiationException {
        AbstractDictMap dictMap = (AbstractDictMap) dictClass.newInstance();
        String str = parseMultiKey(dictMap,key,obj2) + separator;
        try{
            Class clazz = obj1.getClass();
            Field[] fields = obj1.getClass().getDeclaredFields();
            int i = 1;
            for (Field field : fields){
                if("serialVersionUID".equals(field.getName())){
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(),clazz);
                Method getMethod = pd.getReadMethod();

                Object o1 = getMethod.invoke(obj1);
                Object o2 = obj2.get(StrUtil.lowerFirst(getMethod.getName().substring(3)));

                if(o1==null || o2==null){
                    continue;
                }
                if(o1 instanceof Date){
                    o1 = DateUtil.formatDate((Date) o1);
                }else if(o1 instanceof Integer){
                    o2 = Integer.parseInt(o2.toString());
                }
                if(!o1.toString().equals(o2.toString())){
                    if(i!=1){
                        str += separator;
                    }
                    String fieldName = dictMap.get(field.getName());
                    String fieldWrapperMethodName = dictMap.getFieldWarpperMethodName(field.getName());
                    if(fieldWrapperMethodName!=null){
                        Object o1Wrapper = DictFieldWrapperFactory.createFieldWrapper(o1,fieldWrapperMethodName);
                        Object o2Wrapper = DictFieldWrapperFactory.createFieldWrapper(o2,fieldWrapperMethodName);
                        str += "字段名称：" + fieldName + ",旧值："+o1Wrapper + ",新值："+o2Wrapper;
                    }else{
                        str += "字段名称：" + fieldName + ",旧值："+o1 + ",新值："+o2;
                    }
                    i++;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }

    public static String contrastObjByName(Class dictClass, String key, Object obj1, Map<String,String> obj2)
            throws IllegalAccessException, InstantiationException {
        AbstractDictMap dictMap = (AbstractDictMap) dictClass.newInstance();
        String str = parseMultiKey(dictMap,key,obj2) + separator;
        try{
            Class clazz = obj1.getClass();
            Field[] fields = obj1.getClass().getDeclaredFields();
            int i = 1;
            for (Field field : fields){
                if("serialVersionUID".equals(field.getName())){
                    continue;
                }
                String prefix = "get";
                int prefixLength = 3;
                if(field.getType().getName().equals("java.lang.Boolean")){
                    prefix = "is";
                    prefixLength = 2;
                }
                Method getMethod = null;
                try{
                    getMethod = clazz.getDeclaredMethod(prefix + StrUtil.upperFirst(field.getName()));
                }catch (NoSuchMethodException e){
                    System.out.println("this class name:" + clazz.getName() + "is not method Name:" +e.getMessage() );
                    continue;
                }

                Object o1 = getMethod.invoke(obj1);
                Object o2 = obj2.get(StrUtil.lowerFirst(getMethod.getName().substring(prefixLength)));

                if(o1==null || o2==null){
                    continue;
                }
                if(o1 instanceof Date){
                    o1 = DateUtil.formatDate((Date) o1);
                }else if(o1 instanceof Integer){
                    o2 = Integer.parseInt(o2.toString());
                }
                if(!o1.toString().equals(o2.toString())){
                    if(i!=1){
                        str += separator;
                    }
                    String fieldName = dictMap.get(field.getName());
                    String fieldWrapperMethodName = dictMap.getFieldWarpperMethodName(field.getName());
                    if(fieldWrapperMethodName!=null){
                        Object o1Wrapper = DictFieldWrapperFactory.createFieldWrapper(o1,fieldWrapperMethodName);
                        Object o2Wrapper = DictFieldWrapperFactory.createFieldWrapper(o2,fieldWrapperMethodName);
                        str += "字段名称：" + fieldName + ",旧值："+o1Wrapper + ",新值："+o2Wrapper;
                    }else{
                        str += "字段名称：" + fieldName + ",旧值："+o1 + ",新值："+o2;
                    }
                    i++;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }

    public static String parseMultiKey(AbstractDictMap dictMap,String key,Map<String,String> requests){
        StringBuilder sb=new StringBuilder();
        if(key.contains(",")){
            String[] keys = key.split(",");
            for (String item : keys){
                String fieldWrapperMethodName = dictMap.getFieldWarpperMethodName(item);
                String value = requests.get(item);
                if(fieldWrapperMethodName!=null){
                    Object valueWrapper = DictFieldWrapperFactory.createFieldWrapper(value,fieldWrapperMethodName);
                    sb.append(dictMap.get(item)).append("=").append(valueWrapper).append(",");
                }else{
                    sb.append(dictMap.get(item)).append("=").append(value).append(",");
                }
            }
            return StrUtil.removeSuffix(sb.toString(),",");
        }else{
            String fieldWrapperMethodName = dictMap.getFieldWarpperMethodName(key);
            String value = requests.get(key);
            if(fieldWrapperMethodName!=null){
                Object valueWrapper = DictFieldWrapperFactory.createFieldWrapper(value,fieldWrapperMethodName);
                sb.append(dictMap.get(key)).append("=").append(valueWrapper).append(",");
            }else{
                sb.append(dictMap.get(key)).append("=").append(value).append(",");
            }
            return sb.toString();
        }
    }
}
