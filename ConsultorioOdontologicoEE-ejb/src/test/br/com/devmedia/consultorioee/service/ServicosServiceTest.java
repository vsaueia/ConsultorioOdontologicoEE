/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devmedia.consultorioee.service;

import br.com.devmedia.consultorioee.builder.ServicoBuilder;
import br.com.devmedia.consultorioee.model.Servico;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vsaueia
 */
public class ServicosServiceTest {

    private Servico servico1;
    private Servico servico2;
    private Servico servico3;

    private EJBContainer container;
    private ServicosService servicosService;

    public ServicosServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws NamingException {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws NamingException {
        container = EJBContainer.createEJBContainer();
        servicosService = (ServicosService) container.getContext().lookup("java:global/classes/ServicosService");

        servico1 = new ServicoBuilder().comDescricao("Servico 1")
                .comCusto(BigDecimal.TEN).create();
        
        servico2 = new ServicoBuilder().comDescricao("Servico 2")
                .comCusto(BigDecimal.ZERO).create();
        
        servico3 = new ServicoBuilder().comDescricao("Servico 3")
                .comCusto(BigDecimal.ONE).create();

        servico1 = servicosService.addServico(servico1);
        servico2 = servicosService.addServico(servico2);
        servico3 = servicosService.addServico(servico3);
    }

    @After
    public void tearDown() {
        servicosService.removeServico(servico1);
        servicosService.removeServico(servico2);
        servicosService.removeServico(servico3);
        container.close();        
    }

    /**
     * Test of findById method, of class UserService.
     */
    @Test
    public void testFindById() throws Exception {
        System.out.println("findById");
        Servico expResult = servico2;
        Servico result = servicosService.findById(servico2.getId());
        assertEquals(expResult, result);
    }

    /**
     * Test of setUser method, of class UserService.
     */
    @Test
    public void testSetServico() throws Exception {
        System.out.println("setService");
        Servico service = servico3;
        Servico expResult = servico3;
        service.setDescricao("ChangedSeviceName " + new Random().nextInt());
        Servico result = servicosService.updateServico(service);
        Servico resultFromGet = servicosService.findById(service.getId());
        assertEquals(expResult.getDescricao(), result.getDescricao());
        assertEquals(resultFromGet, result);
        assertEquals(resultFromGet.getDescricao(), result.getDescricao());
    }

    /**
     * Test of removeUser method, of class UserService.
     */
    @Test
    public void testRemoveService() throws Exception {
        System.out.println("removeService");
        // Mock User Object
        Servico service = new ServicoBuilder().comDescricao("SRV1").comCusto(BigDecimal.ZERO).create();
        service = servicosService.addServico(service);
        servicosService.removeServico(service);
        Servico serviceRemoved = servicosService.findById(service.getId());
        assertNull(serviceRemoved);
    }
    
    /**
     * Test of addUser method, of class UserService.
     */
    @Test
    public void testAddService() throws Exception {
        System.out.println("addService");
        // Mock User Object
        Servico service = new ServicoBuilder()
                .comDescricao("srv1TestAdd")
                .comCusto(BigDecimal.TEN)
                .create();
               
        Servico result = servicosService.addServico(service);
        Servico resultFromGet = servicosService.findById(service.getId());
        assertEquals(result, resultFromGet);
        servicosService.removeServico(resultFromGet);
    }

    /**
     * Test of findByLoginPassword method, of class UserService.
     */
    @Test
    public void testFindByName() throws Exception {
        System.out.println("findByName");
        String name = servico1.getDescricao();
        Servico expResult = servico1;
        Servico result = servicosService.findByDescricao(name).get(0);
        assertEquals(expResult, result);
    }

    /**
     * Test of findAll method, of class UserService.
     */
    @Test
    public void testFindAll() throws Exception {
        System.out.println("getUsers");
        List<Servico> expResult = new ArrayList<>();
        expResult.add(servico1);
        expResult.add(servico2);
        expResult.add(servico3);        
        List<Servico> result = servicosService.findAll();
        assertTrue(expResult.size() == result.size());
    }

}
