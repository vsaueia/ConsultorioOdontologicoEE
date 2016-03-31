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
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author vsaueia
 */
@Entity
@Table(name = "orcamento_item")
@NamedQueries({
    @NamedQuery(name = "OrcamentoItem.findAll", query = "SELECT o FROM OrcamentoItem o"),
    @NamedQuery(name = "OrcamentoItem.findById", query = "SELECT o FROM OrcamentoItem o WHERE o.id = :id"),
    @NamedQuery(name = "OrcamentoItem.findByTotal", query = "SELECT o FROM OrcamentoItem o WHERE o.total = :total"),
    @NamedQuery(name = "OrcamentoItem.findItensByOrcamentoId", query = "SELECT o FROM OrcamentoItem o WHERE o.orcamento.id = :orcamentoId")
})
public class OrcamentoItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;    
    @NotNull
    @Column(name = "total")
    private BigDecimal total = BigDecimal.ZERO;
    @Column(name = "quantidade")
    private int quantidade = 1;
    @JoinColumn(name = "orcamento_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Orcamento orcamento;
    @JoinColumn(name = "servico_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Servico servico;

    @PrePersist
    @PreUpdate
    public void recalcularTotal() {
        this.total = getValorParcial();
    }

    public OrcamentoItem() {
    }

    public OrcamentoItem(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
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
        if (!(object instanceof OrcamentoItem)) {
            return false;
        }
        OrcamentoItem other = (OrcamentoItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.devmedia.consultorioee.model.OrcamentoItem[ id=" + id + " ]";
    }

    public BigDecimal getValorParcial() {
        if (getServico() == null) {
            return BigDecimal.ZERO;
        }
        return getServico().getCusto().multiply(new BigDecimal(getQuantidade()));
    }
}
