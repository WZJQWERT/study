package com.common.platform.sys.factory;

import com.common.platform.base.exception.BizExceptionEnum;
import com.common.platform.base.exception.ServiceException;

import java.lang.reflect.Method;

public class DictFieldWrapperFactory {

    public static Object createFieldWrapper(Object parameter,String methodName){
        IConstantFactory constantFactory =ConstantFactory.me();
        try{
            Method method = IConstantFactory.class.getMethod(methodName,parameter.getClass());
            return method.invoke(constantFactory,parameter);
        }catch (Exception e){
            try{
                Method method = IConstantFactory.class.getMethod(methodName,Long.class);
                return method.invoke(constantFactory,Long.parseLong(parameter.toString()));
            }catch (Exception ex){
                throw new ServiceException(BizExceptionEnum.ERROR_WRAPPER_FIELD);
            }
        }
    }
}
