package cz.geek.ares.service;

import cz.geek.ares.basic.AresOdpovedi;
import cz.geek.ares.basic.VypisBasic;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static net.jadler.Jadler.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AresBasicServiceTest {

    private AresBasicService service;

    @Before
    public void setUp() {
        initJadler();
        service = new AresBasicService("localhost", port());
    }

    @After
    public void tearDown() {
        closeJadler();
    }

    @Test
    public void testFindByIco() throws Exception {
        onRequest()
                .havingMethodEqualTo("GET")
                .havingURIEqualTo("/cgi-bin/ares/darv_bas.cgi")
                .havingParameterEqualTo("ico", "27074358")
                .respond()
                .withBody(getClass().getResourceAsStream("/basic-response.xml"))
                .withContentType("application/xml");
        AresOdpovedi aresOdpovedi = service.findByIco("27074358");
        assertNotNull(aresOdpovedi);
        VypisBasic vypisBasic = aresOdpovedi.getOdpoved().get(0).getVBAS().get(0);
        assertEquals("Asseco Central Europe, a.s.", vypisBasic.getOF().getValue());

    }

    @Test
    public void testFindByIcoFail() throws Exception {
        onRequest()
                .havingMethodEqualTo("GET")
                .havingURIEqualTo("/cgi-bin/ares/darv_bas.cgi")
                .havingParameterEqualTo("ico", "2707435")
                .respond()
                .withBody(getClass().getResourceAsStream("/basic-error.xml"))
                .withContentType("application/xml");
        AresOdpovedi aresOdpovedi = service.findByIco("2707435");
        assertNotNull(aresOdpovedi);
    }
}
