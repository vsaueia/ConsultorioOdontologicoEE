/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devmedia.consultorioee.builder;

import br.com.devmedia.consultorioee.model.Usuario;

/**
 *
 * @author vsaueia
 */
public class UsuarioBuilder {
    
    private final Usuario usuario;
    
    public UsuarioBuilder() {
        usuario = new Usuario();
    }

    public UsuarioBuilder comNome(String nome) {
        usuario.setNome(nome);
        return this;
    }

    public UsuarioBuilder comAcessosAdminEDentista(boolean admin, boolean dentista) {
        usuario.setAdministrador(admin);
        usuario.setDentista(dentista);
        return this;
    }

    public UsuarioBuilder comLoginAndPassword(String login, String password) {
        usuario.setLogin(login);
        usuario.setPassword(password);
        
        return this;
    }
    
    public Usuario create() {
        return usuario;
    }
    
    
}
