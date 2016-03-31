/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devmedia.consultorioee.service;

import br.com.devmedia.consultorioee.builder.UsuarioBuilder;
import br.com.devmedia.consultorioee.model.Usuario;
import br.com.devmedia.consultorioee.service.repository.UsuarioRepository;
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
public class UsuarioServiceTest {
    private static final String SENHA = "123456";

    private Usuario usuario1;
    private Usuario usuario2;
    private Usuario usuario3;

    private EJBContainer container;
    private UsuarioService usuarioService;

    public UsuarioServiceTest() {
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
        usuarioService = (UsuarioService) container.getContext().lookup("java:global/classes/UsuarioService");

        usuario1 = new UsuarioBuilder()
                .comNome("userName1")
                .comAcessosAdminEDentista(true, false)
                .comLoginAndPassword("testLogin", SENHA)
                .create();

        usuario2 = new UsuarioBuilder()
                .comNome("userName2")
                .comAcessosAdminEDentista(true, true)
                .comLoginAndPassword("testLogin2", SENHA)
                .create();

        usuario3 = new UsuarioBuilder()
                .comNome("userName3")
                .comAcessosAdminEDentista(false, false)
                .comLoginAndPassword("testLogin3", SENHA)
                .create();

        usuario1 = usuarioService.addUsuario(usuario1);
        usuario2 = usuarioService.addUsuario(usuario2);
        usuario3 = usuarioService.addUsuario(usuario3);
    }

    @After
    public void tearDown() {
        usuarioService.removeUsuario(usuario1);
        usuarioService.removeUsuario(usuario2);
        usuarioService.removeUsuario(usuario3);
        usuarioService = null;
        usuario1 = null;
        usuario2 = null;
        usuario3 = null;
        container.close();
        container = null;
    }

    /**
     * Test of findById method, of class UsuarioService.
     */
    @Test
    public void testFindById() throws Exception {
        System.out.println("findById");
        Usuario expResult = usuario2;
        Usuario result = usuarioService.findById(usuario2.getId());
        assertEquals(expResult, result);
    }

    /**
     * Test of updateUsuario method, of class UsuarioService.
     */
    @Test
    public void testSetUsuario() throws Exception {
        System.out.println("setUsuario");
        Usuario user = usuario3;
        Usuario expResult = usuario3;
        user.setNome("ChangedUsuarioName " + new Random().nextInt());
        Usuario result = usuarioService.updateUsuario(user);
        Usuario resultFromGet = usuarioService.findById(user.getId());
        assertEquals(expResult.getNome(), result.getNome());
        assertEquals(resultFromGet, result);
        assertEquals(resultFromGet.getNome(), result.getNome());
    }

    /**
     * Test of removeUsuario method, of class UsuarioService.
     */
    @Test
    public void testRemoveUsuario() throws Exception {
        System.out.println("removeUsuario");
        // Mock Usuario Object
        Usuario usuarioLocal = new UsuarioBuilder()
                .comNome("userName3")
                .comAcessosAdminEDentista(false, false)
                .comLoginAndPassword("testLogin3", SENHA)
                .create();
        
        usuarioLocal = usuarioService.addUsuario(usuarioLocal);
        usuarioService.removeUsuario(usuarioLocal);
        Usuario userRemoved = usuarioService.findById(usuarioLocal.getId());
        assertNull(userRemoved);
    }

    /**
     * Test of setPassword method, of class UsuarioService.
     */
    @Test
    public void testSetPassword() throws Exception {
        System.out.println("setPassword");
        String tmpPassword = new Random().nextInt() + "MyChangePassword";
        String md5TmpPassword = UsuarioRepository.getMd5(tmpPassword);
        usuarioService.setPassword(usuario2);
        Usuario user = usuarioService.findById(usuario2.getId());
        assertEquals(user.getPassword(), md5TmpPassword);
    }

    /**
     * Test of addUsuario method, of class UsuarioService.
     */
    @Test
    public void testAddUsuario() throws Exception {
        System.out.println("addUsuario");
        // Mock Usuario Object
        Usuario user = new UsuarioBuilder()
                .comNome("userName1TestAdd")
                .comAcessosAdminEDentista(true, true)
                .comLoginAndPassword("testLoginAdd", SENHA)
                .create();
               
        Usuario result = usuarioService.addUsuario(user);
        Usuario resultFromGet = usuarioService.findById(user.getId());
        assertEquals(result, resultFromGet);
        usuarioService.removeUsuario(resultFromGet);
    }

    /**
     * Test of findByLoginPassword method, of class UsuarioService.
     */
    @Test
    public void testFindByLoginPassword() throws Exception {
        System.out.println("getUsuarioByLoginPassword");
        String login = usuario1.getLogin();
        Usuario expResult = usuario1;
        Usuario result = usuarioService.findByLoginPassword(login, SENHA);
        assertEquals(expResult, result);
    }

    /**
     * Test of findAll method, of class UsuarioService.
     */
    @Test
    public void testFindAll() throws Exception {
        System.out.println("getUsuarios");
        List<Usuario> expResult = new ArrayList<>();
        expResult.add(usuario1);
        expResult.add(usuario2);
        expResult.add(usuario3);        
        List<Usuario> result = usuarioService.findAll();
        assertTrue(expResult.size() == result.size());
    }

}
