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
        @NamedQuery(name = "Imagem.findByOrcamentoIdECategoriaId", query = "" +
                " select new br.com.devmedia.consultorioee.model.Imagem(i.id, i.descricao) " +
                " from Imagem  i where i.orcamento.id = :orcamentoId and i.categoriaImagem.id = :categoriaId")
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
    @JoinColumn(name = "categoria_imagem_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    @XmlTransient
    private CategoriaImagem categoriaImagem;
    @JoinColumn(name = "orcamento_id", referencedColumnName = "id", nullable = false)
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

    public Imagem(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    @PrePersist
    public void inicializarDataInclusao() {
        this.dataInclusao = LocalDateTime.now();
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
