package com.melihinci.skeleton.advice;

import com.melihinci.skeleton.response.BaseResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleInvalidCredentialsException(Exception ex) {
        return ResponseEntity.ok()
                             .body(BaseResponse.builder()
                                               .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                               .message(ex.getMessage())
                                               .traceId(ThreadContext.get("trace_id"))
                                               .build());
    }
}
