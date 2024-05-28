package com.example.reservationsystem.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Enumeration;
import java.util.stream.Collectors;

@Component
public class HttpLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        String headers = getHeadersAsString(request);
        System.out.println("Incoming request data: " +
                "URI=" + request.getRequestURI() +
                ", Method=" + request.getMethod() +
                ", Headers=" + headers +
                ", QueryString=" + request.getQueryString());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Request handled: " +
                "URI=" + request.getRequestURI() +
                ", Method=" + request.getMethod() +
                ", Status=" + response.getStatus() +
                ", Execution time=" + executionTime + "ms");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            System.out.println("Exception: " + ex.getMessage());
        }
        System.out.println("Request completed: URI=" + request.getRequestURI());
    }

    private String getHeadersAsString(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        return headerNames != null ?
                Collections.list(headerNames).stream()
                        .map(headerName -> headerName + ": " + request.getHeader(headerName))
                        .collect(Collectors.joining(", ")) : "";
    }
}