package com.jia.tanhua.server.filter;

import org.springframework.web.bind.annotation.RequestHeader;

public class BaseContext {
    private  static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setToken(@RequestHeader String token){
        threadLocal.set(token);
    }
    public static String getToken(){
        return threadLocal.get();
    }
 }
