package com.melihinci.skeleton.advice;

import com.melihinci.skeleton.entity.DispatchLog;
import com.melihinci.skeleton.repository.DispatchLogRepository;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Component
public class ExchangeLoggingFilter implements GlobalFilter, Ordered {

    @Autowired
    DispatchLogRepository dispatchLogRepository;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        String traceId;
        if (exchange.getRequest()
                    .getHeaders()
                    .get("X-Trace-Id") != null) {
            traceId = exchange.getRequest()
                              .getHeaders()
                              .get("X-Trace-Id")
                              .get(0);
            ThreadContext.put("trace_id", traceId);
        } else {
            traceId = UUID.randomUUID()
                          .toString();
            ThreadContext.put("trace_id", traceId);
            exchange.getRequest()
                    .getHeaders()
                    .add("X-Trace-Id", traceId);
        }
        DispatchLog dispatchLog = saveDispatchLog(exchange, startTime, traceId);

        return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        long endTime = System.currentTimeMillis();
                        updateDispatchLog(exchange, startTime, endTime, dispatchLog);
                    }));
    }

    private DispatchLog saveDispatchLog(ServerWebExchange exchange, long startTime, String traceId) {
        DispatchLog dispatchLog = new DispatchLog();
        dispatchLog.setTraceId(traceId);
        dispatchLog.setRequestMethod(exchange.getRequest()
                                             .getMethodValue());
        dispatchLog.setRequestTime(LocalDateTime.ofEpochSecond(startTime / 1000, 0, ZoneOffset.UTC));
        dispatchLog.setRequestUri(exchange.getRequest()
                                          .getURI()
                                          .toString());
        dispatchLog.setRequestBody(exchange.getRequest()
                                           .getBody()
                                           .toString());
        dispatchLog.setRequestIp(exchange.getRequest()
                                         .getRemoteAddress()
                                         .getAddress()
                                         .getHostAddress());
        // TODO: change database with faster ones (cassandra)
        return dispatchLogRepository.save(dispatchLog);
    }

    private void updateDispatchLog(ServerWebExchange exchange, long startTime, long endTime, DispatchLog dispatchLog) {
        long duration = endTime - startTime;
        dispatchLog.setResponseStatus(String.valueOf(exchange.getResponse()
                                                             .getStatusCode()));
        dispatchLog.setDuration(duration);
        dispatchLog.setResponseBody(""); // TODO: get response body from exchange
        dispatchLog.setResponseTime(LocalDateTime.ofEpochSecond(endTime / 1000, 0, ZoneOffset.UTC));
        dispatchLogRepository.save(dispatchLog);
    }

    @Override
    public int getOrder() {
        return -1; // The order of the filter, lower value means higher precedence
    }
}
