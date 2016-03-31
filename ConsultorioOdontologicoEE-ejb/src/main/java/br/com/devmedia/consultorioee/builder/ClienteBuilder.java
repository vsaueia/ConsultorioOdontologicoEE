/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devmedia.consultorioee.builder;

import br.com.devmedia.consultorioee.model.Cliente;

import java.util.Date;

/**
 *
 * @author vsaueia
 */
public class ClienteBuilder {

    private final Cliente cliente;

    public ClienteBuilder(Date dataNascimento) {
        cliente = new Cliente();
        cliente.setDataNascimento(dataNascimento);
    }
    
    public Cliente create() {
        return cliente;
    }

    public ClienteBuilder comEnderecoCompleto() {
        cliente.setEndereco("Rua dos java");
        cliente.setCidade("Campo Grande");
        cliente.setEstado("MS");
        cliente.setComplemento("complemento...");
        return this;
    }

    public ClienteBuilder comIdade(int idade) {
        cliente.setIdade(idade);
        return this;
    }

    public ClienteBuilder comPaiEMae() {
        cliente.setNomePai("João da Silva");
        cliente.setNomeMae("Maria da Silva");
        return this;
    }

    public ClienteBuilder comNome(String nome) {
        cliente.setNome(nome);
        return this;
    }

    public ClienteBuilder comObservacao() {
        cliente.setObservacao("Obs...........");
        return this;
    }

    public ClienteBuilder comOcupacao() {
        cliente.setOcupacao("Full-stack developer");
        return this;
    }

    public ClienteBuilder comTelefoneECelular() {
        cliente.setTelefone("(67)9999-9999");
        cliente.setCelular("(67)9999-9999");
        return this;
    }

    public ClienteBuilder comCamposTrabalho() {
        cliente.setEnderecoComercial("Alameda Sempre Verde");
        cliente.setNomeEmpresa("Empresa de TI LTDA");
        cliente.setObservacaoComercial("None");
        return this;
    }

    public ClienteBuilder comDataNascimento(Date data) {
        cliente.setDataNascimento(data);
        return this;
    }

}
