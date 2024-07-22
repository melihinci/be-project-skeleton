package com.melihinci.skeleton.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
