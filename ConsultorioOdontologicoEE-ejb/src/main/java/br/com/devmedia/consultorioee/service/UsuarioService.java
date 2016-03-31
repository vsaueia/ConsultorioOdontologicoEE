package br.com.devmedia.consultorioee.service;

import br.com.devmedia.consultorioee.model.Usuario;
import br.com.devmedia.consultorioee.service.repository.UsuarioRepository;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author vsaueia
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioService extends BasicService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private UsuarioRepository usuarioRepository;
    
    @PostConstruct
    @PostActivate
    private void build() {
        usuarioRepository = new UsuarioRepository(entityManager);
    }
    
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Usuario updateUsuario(Usuario user) throws EJBException{        
        return usuarioRepository.updateUsuario(user);        
    }
    
    public void removeUsuario(Usuario user){
        usuarioRepository.removeUsuario(user);
    }
    
    public void setPassword(Usuario usuario) {
        usuarioRepository.setPassword(usuario.getPassword(), usuario.getId());
    }
    
    public Usuario addUsuario(Usuario user) {
        return usuarioRepository.addUsuario(user);
    }
    
    public Usuario findByLoginPassword(String login, String password) {
        return usuarioRepository.findUsuarioByLoginAndPassword(login, password);
    }
    
    public List<Usuario> findAll() {
        return usuarioRepository.getUsuarios();
    }
    
    public long countUsuariosCadastrados() {
        return usuarioRepository.countUsuariosCadastrados();
    }
    
    public List<Usuario> findByNomeWithLike(String filtroNome) {
        return usuarioRepository.findByNomeWithLike(filtroNome);
    }

    public Usuario findByNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    public List<Usuario> findAllDentistas() {
        return usuarioRepository.findAllDentistas();
    }
}
