package br.com.devmedia.consultorioee.service;

import br.com.devmedia.consultorioee.builder.AnamineseBuilder;
import br.com.devmedia.consultorioee.builder.ClienteBuilder;
import br.com.devmedia.consultorioee.builder.OrcamentoBuilder;
import br.com.devmedia.consultorioee.builder.OrcamentoItemBuilder;
import br.com.devmedia.consultorioee.builder.ServicoBuilder;
import br.com.devmedia.consultorioee.builder.UsuarioBuilder;
import br.com.devmedia.consultorioee.model.Anaminese;
import br.com.devmedia.consultorioee.model.Cliente;
import br.com.devmedia.consultorioee.model.Orcamento;
import br.com.devmedia.consultorioee.model.OrcamentoItem;
import br.com.devmedia.consultorioee.model.Servico;
import br.com.devmedia.consultorioee.model.TipoPagamento;
import br.com.devmedia.consultorioee.model.Usuario;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author dyego.carmo
 */
public class AnamineseServiceTest {

    private EJBContainer container;
    private AnamineseService anamineseService;
    private ClienteService clienteService;
    private OrcamentoService orcamentoService;
    private ServicosService servicosService;
    private UsuarioService usuarioService;
    private Cliente cliente1;
    private Usuario usuario1;
    private Servico servico1;
    private Orcamento orcamento1;
    private OrcamentoItem orcamentoItem1;
    private Anaminese anaminese1;
    private Anaminese anaminese2;

    public AnamineseServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws NamingException {
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        clienteService = (ClienteService) container.getContext().lookup("java:global/classes/ClienteService");
        anamineseService = (AnamineseService) container.getContext().lookup("java:global/classes/AnamineseService");
        orcamentoService = (OrcamentoService) container.getContext().lookup("java:global/classes/OrcamentoService");
        servicosService = (ServicosService) container.getContext().lookup("java:global/classes/ServicosService");
        usuarioService = (UsuarioService) container.getContext().lookup("java:global/classes/UsuarioService");

        // Mock Object One
        cliente1 = new ClienteBuilder(new Date())
                .comEnderecoCompleto()
                .comIdade(Math.abs(new Random().nextInt(99)))
                .comPaiEMae()
                .comNome("Cliente 1")
                .comObservacao()
                .comOcupacao()
                .comTelefoneECelular()
                .comCamposTrabalho()
                .create();

        // Mock User Object
        usuario1 = new UsuarioBuilder()
                .comAcessosAdminEDentista(false, true)
                .comLoginAndPassword("userLogin", "123456")
                .comNome("Usuário1").create();
        usuario1 = usuarioService.addUsuario(usuario1);

        // Mock Orcamento Object
        orcamento1 = new OrcamentoBuilder()
                .comData(new Date())
                .comDentista(usuario1)
                .comObservacao("observacao preenchida")
                .comNumeroParcelas(1)
                .comTotal(BigDecimal.TEN)
                .comTipoPagamento(TipoPagamento.CREDITO)
                .create();

        // Mock Service Object- One
        servico1 = new ServicoBuilder()
                .comDescricao("Test Anaminese Service One " + new Random().nextInt())
                .comCusto(orcamento1.getTotal())
                .create();

        servico1 = servicosService.addServico(servico1);
        // Mock Of Item
        orcamentoItem1 = new OrcamentoItemBuilder(orcamento1)
                .comTotal(orcamento1.getTotal())
                .comServico(servico1)
                .create();

        cliente1 = clienteService.addCliente(cliente1);
        orcamento1.setCliente(cliente1);
        orcamento1 = orcamentoService.addOrcamento(orcamento1);
        orcamento1.addItem(orcamentoItem1);

        // Anaminese One
        anaminese1 = new AnamineseBuilder()
                .comAlergia(true)
                .comDescricaoAlergia("Intolerancia gluten")
                .comCliente(cliente1)
                .create();

        // Anaminese Two
        anaminese2 = new AnamineseBuilder()
                .comAlergia(false)
                .comCliente(cliente1)
                .create();
        // Persist
        anaminese1 = anamineseService.addAnaminese(anaminese1);
        anaminese2 = anamineseService.addAnaminese(anaminese2);
    }

    @After
    public void tearDown() {
        anamineseService.removeAnaminese(anaminese1);
        anamineseService.removeAnaminese(anaminese2);
        orcamentoService.removeOrcamento(orcamento1);
        servicosService.removeServico(servico1);
        clienteService.removeCliente(cliente1);     
        usuarioService.removeUsuario(usuario1);
        container.close();
    }

    /**
     * Test of getAnaminese method, of class AnamineseService.
     */
    @Test
    public void testGetAnaminese() throws Exception {
        System.out.println("getAnaminese");
        Long anamneseId = anaminese1.getId();
        Anaminese expResult = anaminese1;
        Anaminese result = anamineseService.findById(anamneseId);
        assertEquals(expResult, result);
        assertNotSame(anaminese2, result);
        assertTrue(true);
    }

    /**
     * Test of setAnaminese method, of class AnamineseService.
     */
    @Test
    public void testSetAnaminese() throws Exception {
        System.out.println("setAnaminese");
        String descricaoAlergiaAntiga = anaminese1.getDescricaoAlergia();
        anaminese1.setDescricaoAlergia("alergia a proteína");
        anaminese1 = anamineseService.updateAnaminese(anaminese1);
        assertNotEquals(anaminese1.getDescricaoAlergia(), descricaoAlergiaAntiga);
    }

    /**
     * Test of getAnaminesesByCustomer method, of class AnamineseService.
     */
    @Test
    public void testGetAnaminesesByCustomer() throws Exception {
        System.out.println("getAnaminesesByCustomer");
        Cliente cliente = cliente1;
        List<Anaminese> result = anamineseService.findByCliente(cliente);
        List<Anaminese> result2 = anamineseService.findByCliente(null);
        assertEquals(2, result.size());
        assertNotNull(result);
        assertNotSame(0, result.size());
        assertNotNull(result2);
        assertEquals(0, result2.size());
    }

    /**
     * Test of getAnaminesesByOrcamento method, of class AnamineseService.
     */
    @Test()
    public void testGetAnaminesesByOrcamento() throws Exception {
        System.out.println("getAnaminesesByOrcamento");
        List<Anaminese> result = anamineseService.findByOrcamento(orcamento1);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

}
