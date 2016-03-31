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

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vsaueia
 */
@Entity
@Table(name = "servico")
@NamedQueries({
    @NamedQuery(name = "Servico.findAll", query = "SELECT s FROM Servico s"),
    @NamedQuery(name = "Servico.findById", query = "SELECT s FROM Servico s WHERE s.id = :id"),
    @NamedQuery(name = "Servico.findByDescricao", query = "SELECT s FROM Servico s WHERE upper(s.descricao) like upper(:descricao)"),
    @NamedQuery(name = "Servico.findByCusto", query = "SELECT s FROM Servico s WHERE s.custo = :custo"),
    @NamedQuery(name = "Servico.countServicos", query = "SELECT count(s) FROM Servico s"),
    @NamedQuery(name = "Servico.findByDescricaoExata", query = "SELECT s FROM Servico s WHERE upper(s.descricao) = upper(:descricao)")
})
public class Servico implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Size(min = 1, max = 255, message = "A descrição deve conter pelo menos 3 caracteres")
    @Column(name = "descricao")
    private String descricao;
    @NotNull(message = "O custo não pode estar vazio")
    @Column(name = "custo", precision = 16, scale = 2)
    private BigDecimal custo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servico")
    private List<OrcamentoItem> orcamentoItens = new ArrayList<>();

    public Servico() {
    }

    public Servico(Long id) {
        this.id = id;
    }

    public Servico(Long id, String descricao, BigDecimal custo) {
        this.id = id;
        this.descricao = descricao;
        this.custo = custo;
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

    public BigDecimal getCusto() {
        return custo;
    }

    public void setCusto(BigDecimal custo) {
        this.custo = custo;
    }

    public List<OrcamentoItem> getOrcamentoItens() {
        return orcamentoItens;
    }

    public void setOrcamentoItens(List<OrcamentoItem> orcamentoItens) {
        this.orcamentoItens = orcamentoItens;
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
        if (!(object instanceof Servico)) {
            return false;
        }
        Servico other = (Servico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.devmedia.consultorioee.model.Servico[ id=" + id + " ]";
    }
    
}
