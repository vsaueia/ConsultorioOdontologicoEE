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
import br.com.devmedia.consultorioee.builder.ServicoBuilder;
import br.com.devmedia.consultorioee.builder.UsuarioBuilder;
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
public class OrcamentoServiceTest {

    private EJBContainer container;
    private OrcamentoService orcamentoService;
    private ServicosService servicosService;
    private ClienteService clienteService;
    private UsuarioService usuarioService;
    
    private Cliente cliente1;
    private Usuario usuario1;
    private Servico servico1;
    private Orcamento orcamento1;
    private Orcamento orcamento2;    
    private OrcamentoItem orcamentoItem2;

    public OrcamentoServiceTest() {
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
        orcamentoService = (OrcamentoService) container.getContext().lookup("java:global/classes/OrcamentoService");
        servicosService = (ServicosService) container.getContext().lookup("java:global/classes/ServicosService");
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
                .comCamposTrabalho().create();
        cliente1 = clienteService.addCliente(cliente1);

        // Mock User Object
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
                .comNumeroParcelas(1)
                .comTotal(BigDecimal.TEN)
                .comTipoPagamento(TipoPagamento.CREDITO)
                .create();
        
        // Mock Service Object- One
        servico1 = new ServicoBuilder()
                .comDescricao("Test Anaminese Service One "+new Random().nextInt())
                .comCusto(orcamento1.getTotal())
                .create();
        servico1 = servicosService.addServico(servico1);
        
        // Mock Orcamento Object
        orcamento2 = new OrcamentoBuilder()
                .comCliente(cliente1)
                .comData(new Date())
                .comDentista(usuario1)
                .comObservacao("observacao preenchida")
                .comNumeroParcelas(1)
                .comTotal(BigDecimal.TEN)
                .comTipoPagamento(TipoPagamento.DEBITO)
                .create();         
        
        // Mock Of Item
        orcamentoItem2 = new OrcamentoItemBuilder(orcamento2)
                .comTotal(orcamento1.getTotal())
                .comServico(servico1)
                .create();
        orcamento2.addItem(orcamentoItem2);

        // Persist
        orcamento1 = orcamentoService.addOrcamento(orcamento1);
        orcamento2 = orcamentoService.addOrcamento(orcamento2);
    }

    @After
    public void tearDown() {
        orcamentoService.removeOrcamento(orcamento1);
        orcamentoService.removeOrcamento(orcamento2);
        servicosService.removeServico(servico1);
        clienteService.removeCliente(cliente1);
        usuarioService.removeUsuario(usuario1);
        container.close();
    }

    /**
     * Test of getOrcamento method, of class OrcamentoService.
     */
    @Test
    public void testGetOrcamento() throws Exception {
        System.out.println("getOrcamento");        
        Orcamento result = orcamentoService.findById(orcamento1.getId());
        assertEquals(orcamento1, result);
    }

    /**
     * Test of findByCliente method, of class OrcamentoService.
     */
    //@Test
    public void testGetOrcamentos() throws Exception {
        System.out.println("getOrcamentos");
        Long clienteId = null;
        List<Orcamento> expResult = null;
        List<Orcamento> result = orcamentoService.findByClienteId(clienteId);
        assertEquals(expResult, result);
    }

    /**
     * Test of getItens method, of class OrcamentoService.
     */
    //@Test
    public void testGetItens() throws Exception {
        System.out.println("getItens");
        Long orcamentoId = null;
        List<OrcamentoItem> expResult = null;
        List<OrcamentoItem> result = orcamentoService.findItensByOrcamentoId(orcamentoId);
        assertEquals(expResult, result);
    }

}
