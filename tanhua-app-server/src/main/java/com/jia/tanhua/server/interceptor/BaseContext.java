package com.jia.tanhua.server.interceptor;


import com.jia.tanhua.domain.User;

public class BaseContext {
    private  static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void set(User user){
        threadLocal.set(user);
    }
    public static void get(User user){
        threadLocal.get();
    }
    public static Long getUserId(){
        return threadLocal.get().getId();
    }

    public static String getUserPhone(){
        return threadLocal.get().getMobile();

    }

 }
