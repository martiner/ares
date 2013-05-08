package cz.geek.ares.service;

import cz.geek.ares.basic.AresOdpovedi;

import static org.apache.commons.lang.Validate.notEmpty;

/**
 * http://wwwinfo.mfcr.cz/ares/ares_xml_basic.html
 */
public class AresBasicService extends AbstractAresService {

    private static final String BASIC_ICO = "http://{server}:{port}/cgi-bin/ares/darv_bas.cgi?ico={ico}";

    public AresBasicService() {
    }

    public AresBasicService(String server, int port) {
        super(server, port);
    }

    public AresOdpovedi findByIco(String ico) {
        notEmpty(ico, "ico can't be empty");
        AresOdpovedi resp = template.getForObject(BASIC_ICO, AresOdpovedi.class, server, port, ico);
        if (resp == null || resp.getOdpoved() == null) {
            throw new AresBasicServiceException("Received null response", resp);
        }
        return resp;
    }

}
