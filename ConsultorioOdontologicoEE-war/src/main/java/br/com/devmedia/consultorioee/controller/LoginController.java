package br.com.devmedia.consultorioee.controller;

import br.com.devmedia.consultorioee.model.Usuario;
import br.com.devmedia.consultorioee.service.ServicosService;
import br.com.devmedia.consultorioee.service.UsuarioService;
import br.com.devmedia.consultorioee.util.JsfUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author vsaueia
 */
@Named("loginController")
@SessionScoped
public class LoginController extends BasicController implements Serializable {
    private static String DEFAULT_MESSAGE = "Usuário ou senha não informados ou inválidos";
    @EJB
    private UsuarioService usuarioService;
    @EJB
    private ServicosService servicosService;
    
    private Usuario usuarioLogado;
    
    @NotNull
    @NotEmpty(message = "Usuário ou senha não informados ou inválidos")
    private String usuario;
    @NotNull
    @NotEmpty(message = "Usuário ou senha não informados ou inválidos")
    @Length(min = 3, max = 18, message = "A senha deve conter entre 3 e 18 caracteres")
    private String senha;

    @PostConstruct
    public void init() {
        System.out.println("Nova sessão criada > "+hashCode());
    }
    
    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String doLogin() {
        usuarioLogado = null;
        usuarioLogado = usuarioService.findByLoginPassword(usuario, senha);
        if(usuarioLogado == null) {
            JsfUtils.addError(DEFAULT_MESSAGE);
            return "login.faces";
        } else {
            return "/pages/index.faces?faces-redirect=true";
        }
    }
    
    public long getQuantidadeUsuarios() {
        return usuarioService.countUsuariosCadastrados();
    }
    
    public long getQuantidadeServicos() {
        return servicosService.countServicosCadastrados();
    }

}
