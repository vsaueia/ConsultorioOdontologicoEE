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

import br.com.devmedia.consultorioee.util.FormatadoresUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author vsaueia
 */
@Entity
@Table(name = "cliente")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findById", query = "SELECT c FROM Cliente c WHERE c.id = :id"),
    @NamedQuery(name = "Cliente.findByNome", query = "SELECT c FROM Cliente c WHERE upper(c.nome) like upper(:nome)"),
    @NamedQuery(name = "Cliente.findByIdade", query = "SELECT c FROM Cliente c WHERE c.idade = :idade"),
    @NamedQuery(name = "Cliente.findByEndereco", query = "SELECT c FROM Cliente c WHERE c.endereco = :endereco"),
    @NamedQuery(name = "Cliente.findByEstado", query = "SELECT c FROM Cliente c WHERE c.estado = :estado"),
    @NamedQuery(name = "Cliente.findByCidade", query = "SELECT c FROM Cliente c WHERE c.cidade = :cidade"),
    @NamedQuery(name = "Cliente.findByNomePai", query = "SELECT c FROM Cliente c WHERE c.nomePai = :nomePai"),
    @NamedQuery(name = "Cliente.findByNomeMae", query = "SELECT c FROM Cliente c WHERE c.nomeMae = :nomeMae"),
    @NamedQuery(name = "Cliente.findByObservacao", query = "SELECT c FROM Cliente c WHERE c.observacao = :observacao"),
    @NamedQuery(name = "Cliente.findByComplemento", query = "SELECT c FROM Cliente c WHERE c.complemento = :complemento"),
    @NamedQuery(name = "Cliente.findByOcupacao", query = "SELECT c FROM Cliente c WHERE c.ocupacao = :ocupacao"),
    @NamedQuery(name = "Cliente.findByCelular", query = "SELECT c FROM Cliente c WHERE c.celular = :celular"),
    @NamedQuery(name = "Cliente.findByNomeEmpresa", query = "SELECT c FROM Cliente c WHERE c.nomeEmpresa = :nomeEmpresa"),
    @NamedQuery(name = "Cliente.findByEnderecoComercial", query = "SELECT c FROM Cliente c WHERE c.enderecoComercial = :enderecoComercial"),
    @NamedQuery(name = "Cliente.findByObservacaoComercial", query = "SELECT c FROM Cliente c WHERE c.observacaoComercial = :observacaoComercial"),
    @NamedQuery(name = "Cliente.findByTelefone", query = "SELECT c FROM Cliente c WHERE c.telefone = :telefone"),
    @NamedQuery(name = "Cliente.findByDataNascimento", query = "SELECT c FROM Cliente c WHERE c.dataNascimento = :dataNascimento"),
    @NamedQuery(name = "Cliente.countAll", query = "SELECT count(c) FROM Cliente c")
})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255, message = "Informe o nome")
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idade")
    private int idade;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255, message = "Informe o endereço"
    )
    @Column(name = "endereco")
    private String endereco;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2, message = "Informe o estado")
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255, message = "Informe a cidade")
    @Column(name = "cidade")
    private String cidade;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255, message = "Informe o nome do pai")
    @Column(name = "nome_pai")
    private String nomePai;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255, message = "Informe o nome da mãe")
    @Column(name = "nome_mae")
    private String nomeMae;
    @Size(max = 2147483647)
    @Column(name = "observacao")
    @Lob
    private String observacao;
    @Size(max = 2147483647)
    @Column(name = "complemento")
    private String complemento;
    @Size(max = 45)
    @Column(name = "ocupacao")
    private String ocupacao;
    @Size(max = 16)
    @Column(name = "celular")
    private String celular;
    @Size(max = 45)
    @Column(name = "nome_empresa")
    private String nomeEmpresa;
    @Size(max = 255)
    @Column(name = "endereco_comercial")
    private String enderecoComercial;
    @Size(max = 2147483647)
    @Column(name = "observacao_comercial")
    private String observacaoComercial;
    @Size(max = 16)
    @Column(name = "telefone")
    private String telefone;
    @Basic(optional = false)
    @NotNull(message = "Informe a data de nascimento")
    @Column(name = "data_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    @OrderBy("dataHora desc")
    private List<Orcamento> orcamentoList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Anaminese> anamineseList = new ArrayList<>();
    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @PrePersist
    @PreUpdate
    public void init() {
        setIdade(calcularIdade());
    }

    private int calcularIdade() {
        Calendar dateOfBirth = new GregorianCalendar();
        dateOfBirth.setTime(getDataNascimento());
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
        dateOfBirth.add(Calendar.YEAR, age);
        if (today.before(dateOfBirth)) {
            age--;
        }
        return age;
    }

    public Cliente() {
    }

    public Cliente(Long id) {
        this.id = id;
    }

    public Cliente(Long id, String nome, int idade, String endereco,
            String estado, String cidade, String nomePai, String nomeMae,
            Date dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
        this.estado = estado;
        this.cidade = cidade;
        this.nomePai = nomePai;
        this.nomeMae = nomeMae;
        this.dataNascimento = dataNascimento;
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getEnderecoComercial() {
        return enderecoComercial;
    }

    public void setEnderecoComercial(String enderecoComercial) {
        this.enderecoComercial = enderecoComercial;
    }

    public String getObservacaoComercial() {
        return observacaoComercial;
    }

    public void setObservacaoComercial(String observacaoComercial) {
        this.observacaoComercial = observacaoComercial;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<Orcamento> getOrcamentoList() {
        return orcamentoList;
    }

    public void setOrcamentoList(List<Orcamento> orcamentoList) {
        this.orcamentoList = orcamentoList;
    }

    public List<Anaminese> getAnamineseList() {
        return anamineseList;
    }

    public void setAnamineseList(List<Anaminese> anamineseList) {
        this.anamineseList = anamineseList;
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
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.devmedia.consultorioee.model.Cliente[ id=" + id + " ]";
    }

    private Date getDataUltimoAtendimento() {
        if (!orcamentoList.isEmpty()) {
            return getOrcamentoList().get(0).getDataHora();
        }
        return null;
    }

    public String getDataUltimoAtendimentoFormatada() {
        return getDataUltimoAtendimento() != null
                ? FormatadoresUtil.formatarData(getDataUltimoAtendimento())
                : "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
