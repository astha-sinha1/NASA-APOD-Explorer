package com.astha.nasa_apod.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME, start);

        System.out.println("Incoming Request: " + request.getMethod() + " " + request.getRequestURI());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        long executionTime = end - start;

        System.out.println("Response Status: " + response.getStatus());
        System.out.println("Execution Time: " + executionTime + " ms");

        if (ex != null) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
