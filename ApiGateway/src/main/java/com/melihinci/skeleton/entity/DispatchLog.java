package com.melihinci.skeleton.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
public class DispatchLog {
    @Id
    private String traceId;
    @Column
    private String requestBody;
    @Column
    private String responseBody;
    @Column
    private String requestMethod;
    @Column
    private String requestUri;
    @Column
    private String requestHeaders;
    @Column
    private String responseHeaders;
    @Column
    private String responseStatus;
    @Column
    private LocalDateTime requestTime;
    @Column
    private LocalDateTime responseTime;
    @Column
    private Long duration;
    @Column
    private String requestIp;
}
