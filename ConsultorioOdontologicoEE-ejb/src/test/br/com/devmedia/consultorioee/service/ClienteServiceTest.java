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
import br.com.devmedia.consultorioee.model.Cliente;
import java.util.Collections;
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
public class ClienteServiceTest {

    private EJBContainer container;
    private ClienteService clienteService;
    private Cliente cliente1;
    private Cliente cliente2;
    private Cliente cliente3;

    public ClienteServiceTest() {
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

        cliente2 = new ClienteBuilder(new Date())
                .comEnderecoCompleto()
                .comIdade(Math.abs(new Random().nextInt(99)))
                .comPaiEMae()
                .comNome("Cliente 2")
                .comObservacao()
                .comOcupacao()
                .comTelefoneECelular()
                .comCamposTrabalho().create();

        cliente3 = new ClienteBuilder(new Date())
                .comEnderecoCompleto()
                .comIdade(Math.abs(new Random().nextInt(99)))
                .comPaiEMae()
                .comNome("Cliente 3")
                .comObservacao()
                .comOcupacao()
                .comTelefoneECelular()
                .comCamposTrabalho().create();

        // Persist
        cliente1 = clienteService.addCliente(cliente1);
        cliente2 = clienteService.addCliente(cliente2);
        cliente3 = clienteService.addCliente(cliente3);
    }

    @After
    public void tearDown() {
        clienteService.removeCliente(cliente1);
        clienteService.removeCliente(cliente2);
        clienteService.removeCliente(cliente3);
        container.close();
    }

    /**
     * Test of addCliente method, of class ClienteService.
     */
    @Test
    public void testAddCliente() throws Exception {
        System.out.println("addCliente");
        Cliente cus = new ClienteBuilder(new Date())
                .comEnderecoCompleto()
                .comIdade(Math.abs(new Random().nextInt(99)))
                .comPaiEMae()
                .comNome("Cliente add")
                .comObservacao()
                .comOcupacao()
                .comTelefoneECelular()
                .comCamposTrabalho().create();
        Cliente result = clienteService.addCliente(cus);
        Cliente expResult = clienteService.findById(result.getId());
        assertEquals(result, expResult);
        assertEquals(result.getNome(), expResult.getNome());
        clienteService.removeCliente(result);
    }

    /**
     * Test of updateCliente method, of class ClienteService.
     */
    @Test
    public void testSetCliente() throws Exception {
        System.out.println("setCliente");
        String nameOfCliente = "Cliente Changed Name " + new Random().nextInt();
        Cliente cus = cliente2;
        cus.setNome(nameOfCliente);
        Cliente result = clienteService.updateCliente(cus);
        assertEquals(nameOfCliente, result.getNome());
    }

    /**
     * Test of removeCliente method, of class ClienteService.
     */
    @Test
    public void testRemoveCliente() throws Exception {
        System.out.println("removeCliente");
        Cliente cus = new ClienteBuilder(new Date())
                .comEnderecoCompleto()
                .comIdade(Math.abs(new Random().nextInt(99)))
                .comPaiEMae()
                .comNome("Cliente teste")
                .comObservacao()
                .comOcupacao()
                .comTelefoneECelular()
                .comCamposTrabalho().create();

        Cliente toRemove = clienteService.addCliente(cus);
        Cliente gettedToRemove = clienteService.findById(toRemove.getId());
        assertNotNull(toRemove);
        assertNotNull(gettedToRemove);
        clienteService.removeCliente(gettedToRemove);
        Cliente removed = clienteService.findById(gettedToRemove.getId());
        assertNull(removed);
    }

    /**
     * Test of getCliente method, of class ClienteService.
     */
    @Test
    public void testGetCliente() throws Exception {
        System.out.println("getCliente");
        Cliente expResult = cliente3;
        Cliente result = clienteService.findById(cliente3.getId());
        assertEquals(expResult, result);
    }

    /**
     * Test of getClienteByName method, of class ClienteService.
     */
    @Test
    public void testGetClienteByName() throws Exception {
        System.out.println("getClienteByName");
        assertNotNull(clienteService.findByNome(cliente1.getNome()));
    }

    /**
     * Test of getClientesToCall method, of class ClienteService.
     */
//    @Test
//    public void testGetClientesToCall() throws Exception {
//        System.out.println("getClientesToCall");
//        int month = new Random().nextInt(13);
//        if (month == 0) month++;
//        int year = 2013;
//        
//        Orcamento orc = new OrcamentoBuilder()
//                .comCliente(cliente1)
//                .comDentista(usuario1)                                
//                .comNumeroParcelas(10)
//                .comTotal(BigDecimal.TEN)
//                .comTipoPagamento(TipoPagamento.CREDITO)
//                .create();             
//        
//        List<Cliente> expResult = null;
//        List<Cliente> result = clienteService.findClientesComPagamentoAberto(
//        assertEquals(expResult, result);
//        container.close();
//    }
    /**
     * Test of getClientesComPagamentoEmAberto method, of class ClienteService.
     */
    @Test
    public void testGetClientesComPagamentoEmAberto() throws Exception {
        System.out.println("getClientesComPagamentoEmAberto");
        List<Cliente> result = clienteService.findClientesComPagamentoAberto();
        assertEquals(Collections.emptyList(), result);
    }

}
