package cz.geek.ares.service;

import cz.geek.ares.standard.AresOdpovedi;

import static org.apache.commons.lang.Validate.notEmpty;

/**
 * http://wwwinfo.mfcr.cz/ares/ares_xml_standard.html
 */
public class AresStandardService extends AbstractAresService {

    private static final String STANDARD_ICO = "http://{server}:{port}/cgi-bin/ares/darv_std.cgi?ico={ico}";

    public AresStandardService() {
    }

    public AresStandardService(String server, int port) {
        super(server, port);
    }

    public AresOdpovedi findByIco(String ico) {
        notEmpty(ico, "ico can't be empty");
        AresOdpovedi resp = template.getForObject(STANDARD_ICO, AresOdpovedi.class, server, port, ico);
        if (resp == null || resp.getOdpoved() == null) {
            throw new AresStandardServiceException("Received null response", resp);
        }
        return resp;
    }

}
