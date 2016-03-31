/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devmedia.consultorioee.service.repository;

import br.com.devmedia.consultorioee.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.security.MessageDigest;
import java.util.List;

/**
 *
 * @author vsaueia
 */
public class UsuarioRepository extends BasicRepository {

    private static final long serialVersionUID = 1L;

    public UsuarioRepository(EntityManager entityManger) {
        super(entityManger);
    }

    public Usuario findById(Long id) {
        return getEntity(Usuario.class, id);
    }

    public Usuario updateUsuario(Usuario usuario) {
        return updateEntity(Usuario.class, usuario);
    }

    public void removeUsuario(Usuario usuario) {
        removeEntity(Usuario.class, usuario);
    }

    public Usuario addUsuario(Usuario usuario) {
        usuario.setPassword(getMd5(usuario.getPassword()));
        return addEntity(Usuario.class, usuario);
    }

    public void setPassword(String password, Long usuarioId) {
        String newPassword = getMd5(password);
        Usuario usuario = findById(usuarioId);
        usuario.setPassword(newPassword);
        updateUsuario(usuario);
    }

    public Usuario findUsuarioByLoginAndPassword(String login, String password) {
        TypedQuery<Usuario> query = getEntityManager().createNamedQuery("Usuario.findByUsuLoginPassword", Usuario.class)
                .setParameter("login", login)
                .setParameter("password", getMd5(password));
        return uniqueResultOrNull(query);
    }

    // feito assim só para acompanhar o curso, preferir NamedQuery
    public List<Usuario> getUsuarios() {
        return getPureList(Usuario.class, "select u from Usuario u");
    }

    public List<Usuario> byNome(String nome ){
        return getPureList(Usuario.class, "select u from Usuario u where u.usuName like ?1", nome + LIKE_CHAR);
    }

    public static String getMd5(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("utf-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return digest;
    }

    public long countUsuariosCadastrados() {
        return uniqueResultOrNull(getEntityManager().createNamedQuery("Usuario.countUsuarios", Long.class));
    }

    public List<Usuario> findByNomeWithLike(String filtroNome) {
        return getEntityManager()
                .createQuery("Select u from Usuario u where upper(u.nome) like upper(:nome) ", Usuario.class)
                .setParameter("nome", filtroNome+LIKE_CHAR)
                .getResultList();
    }

    public List<Usuario> findAllDentistas() {
        return getEntityManager().createNamedQuery("Usuario.findAllDentista", Usuario.class).getResultList();
    }

    public Usuario findByNome(String nome) {
        return uniqueResultOrNull(getEntityManager()
                .createNamedQuery("Usuario.findByNome", Usuario.class)
                .setParameter("nome", nome));
    }
}
