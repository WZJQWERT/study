package com.common.platform.sys.auth.entrypoint;

import com.alibaba.fastjson.JSON;
import com.common.platform.auth.exception.enums.AuthExceptionEnum;
import com.common.platform.sys.base.controller.response.ErrorResponseData;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        if("get".equalsIgnoreCase(request.getMethod())
        && !request.getHeader("Accept").contains("application/json")){
            response.sendRedirect(request.getContextPath()+"/global/sessionError");
        }else{
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            ErrorResponseData errorResponseData = new ErrorResponseData(AuthExceptionEnum.NO_PERMISSION.getCode(),
                    AuthExceptionEnum.NO_PERMISSION.getMessage());
            response.getWriter().write(JSON.toJSONString(errorResponseData));
        }
    }
}
