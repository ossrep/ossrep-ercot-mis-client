package com.ossrep.ercot.mis.client;

public class ErcotMisClientException extends RuntimeException {

    public ErcotMisClientException(String message) {
        super(message);
    }

    public ErcotMisClientException(String message, Throwable e) {
        super(message, e);
    }

}
