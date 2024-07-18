package com.melihinci.skeleton.advice;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class LoggingInterceptor implements HandlerInterceptor {


    private final String pod_id = UUID.randomUUID()
            .toString();
    private final Environment env;
    private final String appName;
    private final String pod_version;

    public LoggingInterceptor(Environment env) {
        this.env = env;
        appName = this.env.getProperty("spring.application.name");
        pod_version = this.env.getProperty("spring.application.version");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (request.getHeader("X-Trace-Id") != null) {
            ThreadContext.put("trace_id", request.getHeader("X-Trace-Id"));
        } else {
            ThreadContext.put("trace_id", UUID.randomUUID()
                    .toString());
        }
        ThreadContext.put("thread_id", String.valueOf(Thread.currentThread()
                .getId()));
        ThreadContext.put("authToken", maskAuthToken(request));
        ThreadContext.put("pod_id", pod_id);
        ThreadContext.put("pod_name", appName);
        ThreadContext.put("pod_version", pod_version);
        ThreadContext.put("request_type", request.getMethod());
        ThreadContext.put("request_url", request.getRequestURI() + "?" + request
                .getQueryString());
        try {
            ThreadContext.put("request_body", request.getReader()
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator())));
        } catch (Exception e) {
            ThreadContext.put("request_body", "");
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ThreadContext.clearAll();
    }

    private String maskAuthToken(HttpServletRequest request) {
        String authToken = Optional.ofNullable(request
                .getHeader("X-Auth-Token"))
                .orElse("00000000-0000-0000-0000-000000000000");
        int length = authToken.length();
        if (length <= 6) {
            return authToken;
        }
        return authToken.substring(0, 3) + "****" + authToken
                .substring(length - 3);
    }
}