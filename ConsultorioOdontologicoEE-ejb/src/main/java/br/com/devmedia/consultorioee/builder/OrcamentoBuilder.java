package br.com.devmedia.consultorioee.builder;

import br.com.devmedia.consultorioee.model.Cliente;
import br.com.devmedia.consultorioee.model.Orcamento;
import br.com.devmedia.consultorioee.model.TipoPagamento;
import br.com.devmedia.consultorioee.model.Usuario;

import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author vsaueia
 */
public class OrcamentoBuilder {

    private final Orcamento orcamento;

    public OrcamentoBuilder() {
        orcamento = new Orcamento();
    }
    
    public Orcamento create() {
        return orcamento;
    }

    public OrcamentoBuilder comCliente(Cliente cliente) {
        orcamento.setCliente(cliente);
        return this;
    }
    
    public OrcamentoBuilder comData(Date data) {
        orcamento.setDataHora(data);
        return this;
    }
    
    public OrcamentoBuilder comDentista(Usuario usuario) {
        orcamento.setDentista(usuario);
        return this;
    }

    public OrcamentoBuilder comObservacao(String observacao) {
        orcamento.setObservacao(observacao);
        return this;
    }

    
    public OrcamentoBuilder comNumeroParcelas(int numeroParcelas) {
        orcamento.setNumeroParcelas(numeroParcelas);
        return this;
    }

    
    public OrcamentoBuilder comTotal(BigDecimal total) {
        orcamento.setTotal(total);
        return this;
    }

    
    public OrcamentoBuilder comTipoPagamento(TipoPagamento tipoPagamento) {
        orcamento.setTipoPagamento(tipoPagamento);
        return this;
    }

}
