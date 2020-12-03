package com.common.platform.sys.exception.page;

import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 错误页面的默认跳转（例如请求404的时候就会访问这个默认的解析器）
 */
public class ErrorView implements View {

    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public void render(Map<String, ?> map, HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws Exception {
        httpServletRequest.getRequestDispatcher("/global/error")
                .forward(httpServletRequest,httpServletResponse);
    }
}
