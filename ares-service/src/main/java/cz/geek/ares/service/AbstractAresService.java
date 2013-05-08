package cz.geek.ares.service;

import org.springframework.web.client.RestTemplate;

import static org.apache.commons.lang.Validate.notEmpty;
import static org.apache.commons.lang.Validate.notNull;

public abstract class AbstractAresService {

    public static final String SERVER = "wwwinfo.mfcr.cz";

    protected final RestTemplate template;

    protected final String server;

    protected final int port;

    protected AbstractAresService() {
        this(SERVER, 80);
    }

    protected AbstractAresService(String server, int port) {
        this(new RestTemplate(), server, port);
    }

    protected AbstractAresService(RestTemplate template, String server, int port) {
        notNull(template, "template can't be empty");
        notEmpty(server, "server can't be empty");
        this.template = template;
        this.server = server;
        this.port = port;
    }
}
