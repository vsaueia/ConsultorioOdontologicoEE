/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devmedia.consultorioee.builder;

import br.com.devmedia.consultorioee.model.OrcamentoItem;
import br.com.devmedia.consultorioee.model.Servico;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author vsaueia
 */
public class ServicoBuilder {

    private final Servico servico;

    public ServicoBuilder() {
        servico = new Servico();
    }

    public ServicoBuilder comDescricao(String descricao){
        servico.setDescricao(descricao);
        return this;
    }

    public ServicoBuilder comCusto(BigDecimal custo) {
        servico.setCusto(custo);
        return this;
    }

    public ServicoBuilder comOrcamentoItens (List<OrcamentoItem> orcamentoItens) {
        servico.setOrcamentoItens(orcamentoItens);
        return this;
    }
    
    public Servico create() {
        return servico;
    }

}
