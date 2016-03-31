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
import java.util.Date;

/**
 *
 * @author vsaueia
 */
@Entity
@Table(name = "anaminese")
@NamedQueries({
    @NamedQuery(name = "Anaminese.findAll", query = "SELECT a FROM Anaminese a"),
    @NamedQuery(name = "Anaminese.findById", query = "SELECT a FROM Anaminese a WHERE a.id = :id"),
    @NamedQuery(name = "Anaminese.findByFuma", query = "SELECT a FROM Anaminese a WHERE a.fuma = :fuma"),
    @NamedQuery(name = "Anaminese.findByPraticaExercicio", query = "SELECT a FROM Anaminese a WHERE a.praticaExercicio = :praticaExercicio"),
    @NamedQuery(name = "Anaminese.findByDoencaHereditaria", query = "SELECT a FROM Anaminese a WHERE a.doencaHereditaria = :doencaHereditaria"),
    @NamedQuery(name = "Anaminese.findByDescricaoDoencaHereditaria", query = "SELECT a FROM Anaminese a WHERE a.descricaoDoencaHereditaria = :descricaoDoencaHereditaria"),
    @NamedQuery(name = "Anaminese.findByOperacaoRecente", query = "SELECT a FROM Anaminese a WHERE a.operacaoRecente = :operacaoRecente"),
    @NamedQuery(name = "Anaminese.findByDescricaoOperacaoRecente", query = "SELECT a FROM Anaminese a WHERE a.descricaoOperacaoRecente = :descricaoOperacaoRecente"),
    @NamedQuery(name = "Anaminese.findByMedicacaoContinua", query = "SELECT a FROM Anaminese a WHERE a.medicacaoContinua = :medicacaoContinua"),
    @NamedQuery(name = "Anaminese.findByDescricaoMedicacaoContinua", query = "SELECT a FROM Anaminese a WHERE a.descricaoMedicacaoContinua = :descricaoMedicacaoContinua"),
    @NamedQuery(name = "Anaminese.findByAlergia", query = "SELECT a FROM Anaminese a WHERE a.alergia = :alergia"),
    @NamedQuery(name = "Anaminese.findByDescricaoAlergia", query = "SELECT a FROM Anaminese a WHERE a.descricaoAlergia = :descricaoAlergia"),
    @NamedQuery(name = "Anaminese.findByDst", query = "SELECT a FROM Anaminese a WHERE a.dst = :dst"),
    @NamedQuery(name = "Anaminese.findByDescricaoDst", query = "SELECT a FROM Anaminese a WHERE a.descricaoDst = :descricaoDst"),
    @NamedQuery(name = "Anaminese.findByCliente", query = "SELECT a FROM Anaminese a WHERE a.cliente = :cliente"),
    @NamedQuery(name = "Anaminese.findByOrcamento", query = "SELECT a FROM Orcamento o join o.anaminese a WHERE o = :orcamento"),})
public class Anaminese implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fuma")
    private boolean fuma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pratica_exercicio")
    private boolean praticaExercicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "doenca_hereditaria")
    private boolean doencaHereditaria;
    @Size(max = 255)
    @Column(name = "descricao_doenca_hereditaria")
    private String descricaoDoencaHereditaria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "operacao_recente")
    private boolean operacaoRecente;
    @Size(max = 255)
    @Column(name = "descricao_operacao_recente")
    private String descricaoOperacaoRecente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "medicacao_continua")
    private boolean medicacaoContinua;
    @Size(max = 255)
    @Column(name = "descricao_medicacao_continua")
    private String descricaoMedicacaoContinua;
    @Basic(optional = false)
    @NotNull
    @Column(name = "alergia")
    private boolean alergia;
    @Size(max = 255)
    @Column(name = "descricao_alergia")
    private String descricaoAlergia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dst")
    private boolean dst;
    @Size(max = 255)
    @Column(name = "descricao_dst")
    private String descricaoDst;
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @Column(name = "data_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @Column(name = "comentario")
    @Size(max = 2147483647)
    @Lob
    private String comentario;

    @PrePersist
    public void init() {
        dataHora = new Date();
    }

    public Anaminese() {
    }

    public Anaminese(Long id) {
        this.id = id;
    }

    public Anaminese(Long id, boolean fuma, boolean praticaExercicio, 
            boolean doencaHereditaria, boolean operacaoRecente, 
            boolean medicacaoContinua, boolean alergia, boolean dst) {
        this.id = id;
        this.fuma = fuma;
        this.praticaExercicio = praticaExercicio;
        this.doencaHereditaria = doencaHereditaria;
        this.operacaoRecente = operacaoRecente;
        this.medicacaoContinua = medicacaoContinua;
        this.alergia = alergia;
        this.dst = dst;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getFuma() {
        return fuma;
    }

    public void setFuma(boolean fuma) {
        this.fuma = fuma;
    }

    public boolean getPraticaExercicio() {
        return praticaExercicio;
    }

    public void setPraticaExercicio(boolean praticaExercicio) {
        this.praticaExercicio = praticaExercicio;
    }

    public boolean getDoencaHereditaria() {
        return doencaHereditaria;
    }

    public void setDoencaHereditaria(boolean doencaHereditaria) {
        this.doencaHereditaria = doencaHereditaria;
    }

    public String getDescricaoDoencaHereditaria() {
        return descricaoDoencaHereditaria;
    }

    public void setDescricaoDoencaHereditaria(String descricaoDoencaHereditaria) {
        this.descricaoDoencaHereditaria = descricaoDoencaHereditaria;
    }

    public boolean getOperacaoRecente() {
        return operacaoRecente;
    }

    public void setOperacaoRecente(boolean operacaoRecente) {
        this.operacaoRecente = operacaoRecente;
    }

    public String getDescricaoOperacaoRecente() {
        return descricaoOperacaoRecente;
    }

    public void setDescricaoOperacaoRecente(String descricaoOperacaoRecente) {
        this.descricaoOperacaoRecente = descricaoOperacaoRecente;
    }

    public boolean getMedicacaoContinua() {
        return medicacaoContinua;
    }

    public void setMedicacaoContinua(boolean medicacaoContinua) {
        this.medicacaoContinua = medicacaoContinua;
    }

    public String getDescricaoMedicacaoContinua() {
        return descricaoMedicacaoContinua;
    }

    public void setDescricaoMedicacaoContinua(String descricaoMedicacaoContinua) {
        this.descricaoMedicacaoContinua = descricaoMedicacaoContinua;
    }

    public boolean getAlergia() {
        return alergia;
    }

    public void setAlergia(boolean alergia) {
        this.alergia = alergia;
    }

    public String getDescricaoAlergia() {
        return descricaoAlergia;
    }

    public void setDescricaoAlergia(String descricaoAlergia) {
        this.descricaoAlergia = descricaoAlergia;
    }

    public boolean getDst() {
        return dst;
    }

    public void setDst(boolean dst) {
        this.dst = dst;
    }

    public String getDescricaoDst() {
        return descricaoDst;
    }

    public void setDescricaoDst(String descricaoDst) {
        this.descricaoDst = descricaoDst;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(this == object) {
            return true;
        }
        if (!(object instanceof Anaminese)) {
            return false;
        }
        Anaminese other = (Anaminese) object;
        if ((this.id == null && other.id != null) 
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.devmedia.consultorioee.model.Anaminese[ id=" + id + " ]";
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}
