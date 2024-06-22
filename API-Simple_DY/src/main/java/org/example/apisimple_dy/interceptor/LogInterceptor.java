package org.example.apisimple_dy.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor implements HandlerInterceptor {

    // 实现日志拦截器的逻辑

    // ...
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler){
        // 在请求处理之前执行的逻辑
        System.out.print("LogInterceptor: preHandle");
        System.out.println(request.getRequestURL());

        return true;  // 返回true表示继续执行请求，返回false表示拦截请求

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        //
        System.out.print("LogInterceptor: postHandle");
        System.out.println(request.getRequestURL());
    }
}
