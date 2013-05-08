package cz.geek.ares.service;

import cz.geek.ares.basic.AresOdpovedi;

public class AresBasicServiceException extends RuntimeException {

    private final AresOdpovedi resp;

    public AresBasicServiceException(String message, AresOdpovedi resp) {
        super(message);
        this.resp = resp;
    }

    public AresOdpovedi getResp() {
        return resp;
    }
}
