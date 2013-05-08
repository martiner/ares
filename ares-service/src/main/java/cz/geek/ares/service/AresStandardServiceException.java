package cz.geek.ares.service;

import cz.geek.ares.standard.AresOdpovedi;

public class AresStandardServiceException extends RuntimeException {

    private final AresOdpovedi resp;

    public AresStandardServiceException(String message, AresOdpovedi resp) {
        super(message);
        this.resp = resp;
    }

    public AresOdpovedi getResp() {
        return resp;
    }
}
