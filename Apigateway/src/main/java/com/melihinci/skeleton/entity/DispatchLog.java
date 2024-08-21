package com.melihinci.skeleton.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dispatch_log", schema = "main")
public class DispatchLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trace_id")
    private String traceId;
    @Column(name = "request_body")
    private String requestBody;
    @Column(name = "response_body")
    private String responseBody;
    @Column(name = "request_method")
    private String requestMethod;
    @Column(name = "request_uri")
    private String requestUri;
    @Column(name = "request_headers")
    private String requestHeaders;
    @Column(name = "response_headers")
    private String responseHeaders;
    @Column(name = "response_status")
    private String responseStatus;
    @Column(name = "request_time")
    private LocalDateTime requestTime;
    @Column(name = "response_time")
    private LocalDateTime responseTime;
    @Column(name = "duration")
    private Long duration;
    @Column(name = "request_ip")
    private String requestIp;
}
