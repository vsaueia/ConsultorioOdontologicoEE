/*
 * Copyright (C) 2016 vsaueia
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
package br.com.devmedia.consultorioee.model;

import br.com.devmedia.consultorioee.validator.LoginPadrao;
import br.com.devmedia.consultorioee.validator.UsuarioRequirements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vsaueia
 */
@Entity
@Table(name = "usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"),
    @NamedQuery(name = "Usuario.findByNome", query = "SELECT u FROM Usuario u WHERE u.nome = :nome"),
    @NamedQuery(name = "Usuario.findByLogin", query = "SELECT u FROM Usuario u WHERE u.login = :login"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByAdministrador", query = "SELECT u FROM Usuario u WHERE u.administrador = :administrador"),
    @NamedQuery(name = "Usuario.findAllDentista", query = "SELECT u FROM Usuario u WHERE u.dentista = true"),
    @NamedQuery(name = "Usuario.findByUsuLoginPassword", query = "SELECT u FROM Usuario u WHERE u.login = :login and u.password = :password"),
    @NamedQuery(name = "Usuario.countUsuarios", query = "SELECT count(u) FROM Usuario u")
})
@UsuarioRequirements
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 3, max = 255, message = "O nome deve ter mais de 3 caracteres")
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 5, max = 255, message = "O login deve ter mais de 5 caracteres")
    @Column(name = "login")
    @LoginPadrao
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 6, max = 32, message = "A senha deve ter mais de 6 caracteres")
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "administrador")
    private boolean administrador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dentista")
    private boolean dentista = true;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dentista")
    private List<Orcamento> orcamentoList = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(Long id) {
        this.id = id;
    }

    public Usuario(Long id, String nome, String login, String password, boolean administrador, boolean dentista) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.password = password;
        this.administrador = administrador;
        this.dentista = dentista;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public boolean isDentista() {
        return dentista;
    }

    public void setDentista(boolean dentista) {
        this.dentista = dentista;
    }

    public List<Orcamento> getOrcamentoList() {
        return orcamentoList;
    }

    public void setOrcamentoList(List<Orcamento> orcamentoList) {
        this.orcamentoList = orcamentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.devmedia.consultorioee.model.Usuario[ id=" + id + " ]";
    }
    
}
