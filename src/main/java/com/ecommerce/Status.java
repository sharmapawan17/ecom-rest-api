package com.ecommerce;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Status {
    private boolean success;
    private String message;
    private String traceId;

    public Status(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Status(boolean success, String message, String traceId) {
        this.success = success;
        this.message = message;
        this.traceId = traceId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
