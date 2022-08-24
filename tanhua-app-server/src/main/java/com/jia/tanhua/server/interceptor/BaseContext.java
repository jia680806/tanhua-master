package com.jia.tanhua.server.interceptor;


public class BaseContext {
    private  static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setUserId(Long userId){
        threadLocal.set(userId);
    }
    public static Long getUserId(){
        return threadLocal.get();
    }
 }
