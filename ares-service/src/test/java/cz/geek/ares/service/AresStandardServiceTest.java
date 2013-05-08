package cz.geek.ares.service;

import cz.geek.ares.standard.AdresaARES;
import cz.geek.ares.standard.AresOdpovedi;
import cz.geek.ares.standard.Odpoved;
import cz.geek.ares.standard.VypisBasic;
import cz.geek.ares.standard.Zaznam;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static net.jadler.Jadler.*;
import static org.junit.Assert.*;

public class AresStandardServiceTest {

    private AresStandardService service;

    @Before
    public void setUp() {
        initJadler();
        service = new AresStandardService("localhost", port());
    }

    @After
    public void tearDown() {
        closeJadler();
    }

    @Test
    public void testFindByIco() throws Exception {
        onRequest()
                .havingMethodEqualTo("GET")
                .havingURIEqualTo("/cgi-bin/ares/darv_std.cgi")
                .havingParameterEqualTo("ico", "xx")
                .respond()
                .withBody(getClass().getResourceAsStream("/standard-response.xml"))
                .withContentType("application/xml");
        AresOdpovedi aresOdpovedi = service.findByIco("xx");
        assertNotNull(aresOdpovedi);
        Zaznam zaznam = aresOdpovedi.getOdpoved().get(0).getZaznam().get(0);
        assertEquals("Asseco Central Europe, a.s.", zaznam.getObchodniFirma());

        AdresaARES adresa = zaznam.getIdentifikace().getAdresaARES();
        assertEquals("Budějovická", adresa.getNazevUlice());
        assertEquals(Integer.valueOf(778), adresa.getCisloDomovni());
        assertEquals("3a", adresa.getCisloOrientacni());
        assertEquals("14000", adresa.getPSC());
        assertEquals("Praha", adresa.getNazevObce());
    }

    @Test
    public void testFindByIcoFail() throws Exception {
        onRequest()
                .havingMethodEqualTo("GET")
                .havingURIEqualTo("/cgi-bin/ares/darv_std.cgi")
                .havingParameterEqualTo("ico", "2707435")
                .respond()
                .withBody(getClass().getResourceAsStream("/standard-error.xml"))
                .withContentType("application/xml");
        AresOdpovedi aresOdpovedi = service.findByIco("2707435");
        assertNotNull(aresOdpovedi);
        Odpoved odpoved = aresOdpovedi.getOdpoved().get(0);
        assertNotNull(odpoved);
        assertNotNull(odpoved.getError());
        assertTrue(odpoved.getZaznam().isEmpty()); // jaxb doesn;t return null
    }
}
