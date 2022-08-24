package com.jia.tanhua.server.interceptor;


import com.jia.tanhua.commons.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class TokenInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (!JwtUtils.verifyToken(token)){
            response.setStatus(401);
            return false;
        }
        Claims claims = JwtUtils.getClaims(token);
        Integer id =(Integer) claims.get("id");
        BaseContext.setUserId(Long.valueOf(id));

        return true;

    }
}
