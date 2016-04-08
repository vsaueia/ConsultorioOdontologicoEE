package br.com.devmedia.consultorioee.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "imagem")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Imagem.findAll", query = "SELECT i FROM Imagem i"),
        @NamedQuery(name = "Imagem.findById", query = "SELECT i FROM Imagem i WHERE i.id = :id"),
        @NamedQuery(name = "Imagem.findByDescricao", query = "SELECT i FROM Imagem i WHERE i.descricao = :descricao"),
        @NamedQuery(name = "Imagem.findByOrcamento", query = "" +
                " select new br.com.devmedia.consultorioee.model.Imagem(i.descricao, i.dataInclusao, i.categoriaImagem, i.orcamento) " +
                " from Imagem  i where i.orcamento = :orcamento")
})
public class Imagem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @XmlElement
    private Long id;
    @Basic(optional = false)
    @Column(name = "descricao", nullable = false, length = 255)
    @XmlElement
    private String descricao;
    @Basic(optional = false)
    @Column(name = "data_inclusao", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @XmlTransient
    private LocalDateTime dataInclusao;
    @JoinColumn(name = "categoria", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    @XmlTransient
    private CategoriaImagem categoriaImagem;
    @JoinColumn(name = "orcamento", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    @XmlTransient
    private Orcamento orcamento;
    @Basic(optional = false, fetch = FetchType.LAZY)
    @Column(name = "imagem", nullable = false)
    @Lob
    @XmlTransient
    private byte[] imagem;

    public Imagem() {
    }

    public Imagem(String descricao, LocalDateTime dataInclusao, CategoriaImagem categoriaImagem, Orcamento orcamento) {
        this.descricao = descricao;
        this.dataInclusao = dataInclusao;
        this.categoriaImagem = categoriaImagem;
        this.orcamento = orcamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public CategoriaImagem getCategoriaImagem() {
        return categoriaImagem;
    }

    public void setCategoriaImagem(CategoriaImagem categoriaImagem) {
        this.categoriaImagem = categoriaImagem;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}
