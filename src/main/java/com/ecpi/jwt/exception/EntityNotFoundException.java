package com.ecpi.jwt.exception;

public class EntityNotFoundException extends RuntimeException {

    private int status;

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, int status){
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
