package com.jia.tanhua.server.filter;


import com.jia.tanhua.commons.utils.JwtUtils;
import com.jia.tanhua.server.exception.BusinessException;
import com.jia.tanhua.vo.ErrorResult;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1、获取本次请求的URI
        String url = request.getRequestURI();
        //2、判断本次请求是否需要处理
        if (url.equals("/user/login")) {
            //3、如果不需要处理，则直接放行
            filterChain.doFilter(request, response);
            return;
        }

        String token = BaseContext.getToken();

        //4、判断登录状态，如果已登录，则直接放行
        if(token == null || !JwtUtils.verifyToken(token)){
            throw new BusinessException(ErrorResult.loginError());
        }

        //4、判断登录状态，如果已登录，则直接放行
        filterChain.doFilter(request,response);





    }

}
