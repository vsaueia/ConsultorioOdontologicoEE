/*
 * Copyright (C) 2014 dyego.carmo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package br.com.devmedia.consultorioee.service;


import br.com.devmedia.consultorioee.builder.ClienteBuilder;
import br.com.devmedia.consultorioee.builder.OrcamentoBuilder;
import br.com.devmedia.consultorioee.builder.OrcamentoItemBuilder;
import br.com.devmedia.consultorioee.builder.ParcelaBuilder;
import br.com.devmedia.consultorioee.builder.ServicoBuilder;
import br.com.devmedia.consultorioee.builder.UsuarioBuilder;
import br.com.devmedia.consultorioee.model.Cliente;
import br.com.devmedia.consultorioee.model.Orcamento;
import br.com.devmedia.consultorioee.model.OrcamentoItem;
import br.com.devmedia.consultorioee.model.Parcela;
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
public class FinanceiroServiceTest {
    
    private EJBContainer container;
    private FinanceiroService financeiroService;
    private ServicosService servicosService;
    private ClienteService clienteService;
    private OrcamentoService orcamentoService;
    private UsuarioService usuarioService;
    
    private Parcela parcela1;
    private Parcela parcela2;
    private Parcela parcela3;
    private Cliente cliente1;
    private Usuario usuario1;
    private Servico servico1;
    private Orcamento orcamento1;
    private OrcamentoItem orcamentoItem1;
    
    public FinanceiroServiceTest() {
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
        financeiroService = (FinanceiroService)container.getContext().lookup("java:global/classes/FinanceiroService");
        servicosService = (ServicosService) container.getContext().lookup("java:global/classes/ServicosService");
        orcamentoService = (OrcamentoService) container.getContext().lookup("java:global/classes/OrcamentoService");
        clienteService = (ClienteService) container.getContext().lookup("java:global/classes/ClienteService");
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
        cliente1 = clienteService.addCliente(cliente1);

        // Mock of User
        usuario1 = new UsuarioBuilder()
                .comAcessosAdminEDentista(false, true)
                .comLoginAndPassword("userLogin", "123456")
                .comNome("Usuário1").create();
        usuario1 = usuarioService.addUsuario(usuario1);
        
        // Mock Orcamento Object
        orcamento1 = new OrcamentoBuilder()
                .comCliente(cliente1)
                .comData(new Date())
                .comDentista(usuario1)
                .comObservacao("observacao preenchida")
                .comNumeroParcelas(3)
                .comTotal(BigDecimal.valueOf(2100))
                .comTipoPagamento(TipoPagamento.CREDITO)
                .create();
        
        // Mock Servico Object- One
        servico1 = new ServicoBuilder()
                .comDescricao("Test Anaminese Service One "+new Random().nextInt())
                .comCusto(orcamento1.getTotal())
                .create();
        servico1 = servicosService.addServico(servico1);
//        // Mock Of Item
        orcamentoItem1 = new OrcamentoItemBuilder(orcamento1)
                .comTotal(orcamento1.getTotal())
                .comServico(servico1)
                .create();
        
        orcamento1.addItem(orcamentoItem1);
        orcamento1 = orcamentoService.addOrcamento(orcamento1);
        // Mock of Parcela
        
        BigDecimal vlrParcela = orcamento1.getTotal().divide(BigDecimal.valueOf(3));
        parcela1 = new ParcelaBuilder()
                .comNumero(1)
                .comOrcamento(orcamento1)
                .comValor(vlrParcela)
                .emAberto(false)
                .create();
        
        parcela2 = new ParcelaBuilder()
                .comNumero(2)
                .comOrcamento(orcamento1)
                .comValor(vlrParcela)
                .emAberto(true)
                .create();
        
        parcela3 = new ParcelaBuilder()
                .comNumero(3)
                .comOrcamento(orcamento1)
                .comValor(vlrParcela)
                .emAberto(true)
                .create();
        
        // Persist
        parcela1 = financeiroService.addParcela(parcela1);
        parcela2 = financeiroService.addParcela(parcela2);
        parcela3 = financeiroService.addParcela(parcela3);
    }
    
    @After
    public void tearDown() {
        financeiroService.removeParcela(parcela1);
        financeiroService.removeParcela(parcela2);
        financeiroService.removeParcela(parcela3);
        orcamentoService.removeItem(orcamentoItem1);
        orcamentoService.removeOrcamento(orcamento1);
        servicosService.removeServico(servico1);
        clienteService.removeCliente(cliente1);
        usuarioService.removeUsuario(usuario1);
        container.close();
    }

    /**
     * Test of getParcela method, of class FinanceiroServico.
     */
    @Test
    public void testGetParcela() throws Exception {
        System.out.println("getParcela");
        Parcela expResult = parcela1;
        Parcela result = financeiroService.findById(parcela1.getId());
        assertEquals(expResult, result);
    }

    /**
     * Test of getParcelasByOrcamento method, of class FinanceiroServico.
     */
    @Test
    public void testGetParcelasByOrcamento() throws Exception {
        System.out.println("getParcelasByOrcamento");        
        int expResult = orcamento1.getNumeroParcelas();
        List<Parcela> result = financeiroService.findParcelasByOrcamento(orcamento1);
        assertEquals(expResult, result.size());
    }

    /**
     * Test of getParcelasOfOrcamentoPagas method, of class FinanceiroServico.
     */
    @Test
    public void testGetParcelasOfOrcamentoPagas() throws Exception {
        System.out.println("getParcelasOfOrcamentoPagas");        
        int expResult = 1;
        List<Parcela> result = financeiroService.findParcelasOrcamentoPagas(orcamento1);
        assertEquals(expResult, result.size());
    }

    /**
     * Test of getParcelasOfOrcamentoEmAberto method, of class FinanceiroServico.
     */
    @Test
    public void testGetParcelasOfOrcamentoEmAberto() throws Exception {
        System.out.println("getParcelasOfOrcamentoEmAberto");
        int expResult = 2;
        List<Parcela> result = financeiroService.findParcelasOrcamentoEmAberto(orcamento1);
        assertEquals(expResult, result.size());
    }

    /**
     * Test of getParcelasOfCliente method, of class FinanceiroServico.
     */
    @Test
    public void testGetParcelasOfCliente() throws Exception {
        System.out.println("getParcelasOfCliente");        
        int expResult = 3;
        List<Parcela> result = financeiroService.findParcelasByCliente(cliente1);
        assertEquals(expResult, result.size());
    }

    /**
     * Test of getParcelasOfClientePagas method, of class FinanceiroServico.
     */
    @Test
    public void testGetParcelasOfClientePagas() throws Exception {
        System.out.println("getParcelasOfClientePagas");
        int expResult = 1;
        List<Parcela> result = financeiroService.findParcelasClientePagas(cliente1);
        assertEquals(expResult, result.size());
    }

    /**
     * Test of getParcelasOfClienteEmAberto method, of class FinanceiroServico.
     */
    @Test
    public void testGetParcelasOfClienteEmAberto() throws Exception {
        System.out.println("getParcelasOfClienteEmAberto");
        int expResult = 2;
        List<Parcela> result = financeiroService.findParcelasClienteEmAberto(cliente1);
        assertEquals(expResult, result.size());
    }

    /**
     * Test of setPagamentoParcela method, of class FinanceiroServico.
     */
    @Test
    public void testSetPagamentoParcela() throws Exception {
        System.out.println("setPagamentoParcela");
        boolean expResult = true;
        Parcela result = financeiroService.setPagamentoParcela(parcela2);
        assertEquals(expResult, result.getPago());
    }
    
}
