package com.common.platform.sys.auth.filter;

import com.common.platform.auth.jwt.payload.JwtTokenUtil;
import com.common.platform.base.utils.CoreUtil;
import com.common.platform.sys.auth.cache.SessionManager;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.common.platform.base.consts.ConstantsContext.getTokenHeaderName;

@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private SessionManager sessionManager;

    public JwtAuthorizationTokenFilter(){

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //过滤静态资源
        String[] regs = {"/assets/**","/favicon.ico","/activity-editor/**"};
        for (String reg : regs){
            if(new AntPathMatcher().match(reg,request.getServletPath())){
                chain.doFilter(request,response);
                return;
            }
        }
        String tokenHeader = getTokenHeaderName();
        final String requestHeader = request.getHeader(tokenHeader);

        String username = null;
        String authToken = null;

        //1.从cookie中获取token
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies){
                if(tokenHeader.equals(cookie.getName())){
                    authToken = cookie.getValue();
                }
            }
        }
        //2.如果cookie中没有token，从header中获取
        if(CoreUtil.isEmpty(authToken)){
            if(requestHeader!=null && requestHeader.startsWith("Bearer ")){
                authToken = requestHeader.substring(7);
            }
        }

        //3.通过token获取用户名
        if(CoreUtil.isNotEmpty(authToken)){
            try{
                username = JwtTokenUtil.getJwtPayLoad(authToken).getAccount();
            }catch (IllegalArgumentException | JwtException e){

            }
        }
        //4.如果账号不为空，但是当前security上下文没有设置，就进行设置
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = sessionManager.getSession(authToken);
            if(userDetails==null){
                Cookie[] tempCookies = request.getCookies();
                for (Cookie cookie : tempCookies){
                    if(tokenHeader.equals(cookie.getName())){
                        Cookie temp = new Cookie(cookie.getName(),"");
                        temp.setMaxAge(0);
                        response.addCookie(temp);
                    }
                }
                //跳转到登录超时
                response.setHeader("CommonPlatform-Session-Timeout","true");
                request.getRequestDispatcher("/global/sessionError").forward(request,response);
                return;
            }
            //创建当前登录上下文
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request,response);
    }
}
