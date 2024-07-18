package com.melihinci.skeleton;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@SequenceGenerator(name = "DISPATCH_LOG_SEQ", initialValue = 100000)
public class DispatchLog {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column
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
