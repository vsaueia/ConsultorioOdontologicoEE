package br.com.devmedia.consultorioee.controller;

import br.com.devmedia.consultorioee.model.Usuario;
import br.com.devmedia.consultorioee.service.UsuarioService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vsaueia
 */
@Named
@SessionScoped
public class UsuarioController extends BasicController implements Serializable {
    @EJB
    private UsuarioService usuarioService;
    
    private String filtroNome;
    private List<Usuario> usuariosResult = new ArrayList<>();

    private Usuario usuarioSelecionado = new Usuario();
    
    public String getFiltroNome() {
        return filtroNome;
    }

    public void setFiltroNome(String filtroNome) {
        this.filtroNome = filtroNome;
    }

    public List<Usuario> getUsuariosResult() {
        if(usuariosResult.isEmpty()) {
            usuariosResult = usuarioService.findAll();
        }
        return usuariosResult;
    }

    public void setUsuariosResult(List<Usuario> usuariosResult) {
        this.usuariosResult = usuariosResult;
    }
    
    public String buscar() {
       usuariosResult = usuarioService.findByNomeWithLike(filtroNome);
       return "usuarios.faces";
    }
    
    public String startAdicionarUsuario() {
        usuarioSelecionado = new Usuario();
        return "/pages/adicionarUsuario.faces";
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }
    
    public String finalizarCadastro() {
        if(existsViolations(usuarioSelecionado)) {
            return "/pages/adicionarUsuario.faces";
        }
        
        usuarioService.addUsuario(usuarioSelecionado);
        return cancelar();
    }
    
    public String cancelar() {
        usuarioSelecionado = null;      
        usuariosResult = new ArrayList<>();
        return "/pages/usuarios.faces";
    }
    
    public String editarUsuario() {
        return "/pages/editarUsuario.faces";
    }
    
    public String editarSenhaUsuario() {        
        return "/pages/editarSenhaUsuario.faces";
    }
    
    public String finalizarEdicao() {
        if(existsViolations(usuarioSelecionado)) {
            return "/pages/editarUsuario.faces";
        }
        
        usuarioService.updateUsuario(usuarioSelecionado);
        return cancelar();
    }
    
    public String removerUsuario() {
        usuarioService.removeUsuario(usuarioSelecionado);
        return cancelar();
    }
    
    public String finalizarEdicaoSenha() {
        usuarioService.setPassword(usuarioSelecionado);
        return cancelar();
    }
}
