package com.ecpi.jwt.exception;

public class EntityAlreadyExistsException extends RuntimeException {

    private  int status;

    public EntityAlreadyExistsException(String message) {
        super(message);
    }


    public EntityAlreadyExistsException(String message, int status){
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }


}
