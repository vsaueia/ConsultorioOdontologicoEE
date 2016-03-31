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

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author vsaueia
 */
@Entity
@Table(name = "orcamento")
@NamedQueries({
    @NamedQuery(name = "Orcamento.findAll", query = "SELECT o FROM Orcamento o"),
    @NamedQuery(name = "Orcamento.findById", query = "SELECT o FROM Orcamento o WHERE o.id = :id"),
    @NamedQuery(name = "Orcamento.findByObservacao", query = "SELECT o FROM Orcamento o WHERE o.observacao = :observacao"),
    @NamedQuery(name = "Orcamento.findByTotal", query = "SELECT o FROM Orcamento o WHERE o.total = :total"),
    @NamedQuery(name = "Orcamento.findByTipoPagamento", query = "SELECT o FROM Orcamento o WHERE o.tipoPagamento = :tipoPagamento"),
    @NamedQuery(name = "Orcamento.findByNumeroParcelas", query = "SELECT o FROM Orcamento o WHERE o.numeroParcelas = :numeroParcelas"),
    @NamedQuery(name = "Orcamento.findClientesByData", query = "SELECT o.cliente FROM Orcamento o WHERE o.dataHora between :dataInicial and :dataFinal"),
    @NamedQuery(name = "Orcamento.findClientes", query = "SELECT o FROM Orcamento o WHERE o.cliente = :cliente"),
    @NamedQuery(name = "Orcamento.findItensByOrcamentoId", query = "SELECT o.orcamentoItens FROM Orcamento o WHERE o.id = :orcamentoId"),
    @NamedQuery(name = "Orcamento.findByClienteId", query = "SELECT o FROM Orcamento o WHERE o.cliente.id = :clienteId"),
    @NamedQuery(name = "Orcamento.findByCliente", query = "SELECT o FROM Orcamento o WHERE o.cliente = :cliente")
})
public class Orcamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "data_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "observacao")
    private String observacao;
    @Column(name = "total")
    private BigDecimal total = BigDecimal.ZERO;
    @Column(name = "numero_parcelas")
    private Integer numeroParcelas;
    @Column(name = "tipo_pagamento")
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;
    @ManyToOne(optional = false)
    @JoinColumn(name = "dentista_id", referencedColumnName = "id")
    private Usuario dentista;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orcamento")
    private List<Parcela> parcelaList = new ArrayList<>();
    @OneToOne
    private Anaminese anaminese;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orcamento")
    private List<OrcamentoItem> orcamentoItens = new ArrayList<>();

    @PrePersist
    public void init() {
        dataHora = new Date();
    }

    public Orcamento() {
    }

    public Orcamento(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getDentista() {
        return dentista;
    }

    public void setDentista(Usuario dentista) {
        this.dentista = dentista;
    }

    public List<Parcela> getParcelaList() {
        return parcelaList;
    }

    public void setParcelaList(List<Parcela> parcelaList) {
        this.parcelaList = parcelaList;
    }

    public Anaminese getAnaminese() {
        return anaminese;
    }

    public void setAnaminese(Anaminese anaminese) {
        this.anaminese = anaminese;
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
        if (!(object instanceof Orcamento)) {
            return false;
        }
        Orcamento other = (Orcamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.devmedia.consultorioee.model.Orcamento[ id=" + id + " ]";
    }

    public void addItem(OrcamentoItem orcamentoItem) {
        this.orcamentoItens.add(orcamentoItem);
    }

    public String getItensFormatado() {
        if (orcamentoItens.isEmpty()) {
            return "Nenhum item";
        }
        List<String> nomesServicos = Lists.transform(orcamentoItens, new Function<OrcamentoItem, String>() {
            @Override
            public String apply(OrcamentoItem item) {
                return item.getServico().getDescricao();
            }
        });
        return Joiner.on(',').join(nomesServicos);
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public void recalcularValoresItens() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrcamentoItem item : getOrcamentoItens()) {
            total = total.add(item.getTotal());
        }
        setTotal(total);
    }

}
