package br.com.devmedia.consultorioee.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categoria_imagem")
@NamedQueries({
    @NamedQuery(name = "CategoriaImagem.findAll", query = "SELECT c FROM CategoriaImagem c"),
    @NamedQuery(name = "CategoriaImagem.findByDescricao", query = "Select c from CategoriaImagem  c where upper(c.descricao) like upper(:descricao)"),
    @NamedQuery(name = "CategoriaImagem.countAll", query = "Select count(c) from CategoriaImagem c")
})
public class CategoriaImagem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic(optional = false)
    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaImagem")
    private List<Imagem> imagens = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String nome) {
        this.descricao = nome;
    }

    public List<Imagem> getImagens() {
        return imagens;
    }
}
