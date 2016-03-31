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
@Table(name = "parcela")
@NamedQueries({
    @NamedQuery(name = "Parcela.findAll", query = "SELECT p FROM Parcela p"),
    @NamedQuery(name = "Parcela.findById", query = "SELECT p FROM Parcela p WHERE p.id = :id"),
    @NamedQuery(name = "Parcela.findByNumero", query = "SELECT p FROM Parcela p WHERE p.numero = :numero"),
    @NamedQuery(name = "Parcela.findByValor", query = "SELECT p FROM Parcela p WHERE p.valor = :valor"),
    @NamedQuery(name = "Parcela.findByPago", query = "SELECT p FROM Parcela p WHERE p.pago = :pago"),
    @NamedQuery(name = "Parcela.findByOrcamento", query = "SELECT p FROM Parcela p WHERE p.orcamento = :orcamento"),
    @NamedQuery(name = "Parcela.findByOrcamentoAndStatusPagamento", query = "SELECT p FROM Parcela p "
            + " WHERE p.orcamento = :orcamento and p.pago = :status"),
    @NamedQuery(name = "Parcela.findByCliente", query = "SELECT p FROM Parcela p WHERE p.orcamento.cliente = :cliente order by p.numero"),
    @NamedQuery(name = "Parcela.findByClienteAndStatusPagamento", query = "SELECT p FROM Parcela p "
            + " WHERE p.orcamento.cliente = :cliente and p.pago = :status"),
    @NamedQuery(name = "Parcela.deleteByOrcamento", query = "delete from Parcela p where p.orcamento = :orcamento")
})
public class Parcela implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero")
    private int numero = 1;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private BigDecimal valor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pago")
    private boolean pago;
    @JoinColumn(name = "orcamento_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Orcamento orcamento;

    public Parcela() {
    }

    public Parcela(Long id) {
        this.id = id;
    }

    public Parcela(Long id, int numero, BigDecimal valor, boolean pago) {
        this.id = id;
        this.numero = numero;
        this.valor = valor;
        this.pago = pago;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean getPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
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
        if (!(object instanceof Parcela)) {
            return false;
        }
        Parcela other = (Parcela) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.devmedia.consultorioee.model.Parcela[ id=" + id + " ]";
    }
    
}
