package com.ecommerce.exceotion;

public class DatabaseException extends RuntimeException {
    private String code;
    private String message;

    public DatabaseException(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
