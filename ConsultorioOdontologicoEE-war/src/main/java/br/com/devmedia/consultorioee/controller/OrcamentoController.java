/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devmedia.consultorioee.controller;

import br.com.devmedia.consultorioee.model.*;
import br.com.devmedia.consultorioee.service.FinanceiroService;
import br.com.devmedia.consultorioee.service.OrcamentoService;
import br.com.devmedia.consultorioee.service.ServicosService;
import br.com.devmedia.consultorioee.service.UsuarioService;
import br.com.devmedia.consultorioee.util.JsfUtils;
import net.sf.jasperreports.engine.JRException;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author vsaueia
 */
@Named
@SessionScoped
public class OrcamentoController extends BasicController implements java.io.Serializable {

    @EJB
    private OrcamentoService orcamentoService;
    @EJB
    private ServicosService servicosService;
    @EJB
    private UsuarioService usuarioService;
    @EJB
    private FinanceiroService financeiroService;

    @Inject
    private AnamineseController anamineseController;

    private Cliente clienteSelecionado;
    private Orcamento orcamentoSelecionado;
    private List<Orcamento> orcamentosResult = new ArrayList<>();
    private List<Parcela> parcelasResult = new ArrayList<>();
    private OrcamentoItem orcamentoItemSelecionado;
    private Parcela parcelaSelecionada;

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public void setOrcamentoService(OrcamentoService orcamentoService) {
        this.orcamentoService = orcamentoService;
    }

    public Orcamento getOrcamentoSelecionado() {
        return orcamentoSelecionado;
    }

    public void setOrcamentoSelecionado(Orcamento orcamentoSelecionado) {
        this.orcamentoSelecionado = orcamentoSelecionado;
    }

    public List<Orcamento> getOrcamentosResult() {
        return orcamentosResult;
    }

    public void setOrcamentosResult(List<Orcamento> orcamentosResult) {
        this.orcamentosResult = orcamentosResult;
    }

    public String iniciarAtendimento(Cliente cliente) {
        setClienteSelecionado(cliente);
        anamineseController.setClienteSelecionado(clienteSelecionado);
        anamineseController.cleanCache();
        cleanCache();
        return "/pages/atendimento.faces";
    }

    private void cleanCache() {
        setOrcamentoSelecionado(new Orcamento());
        getOrcamentoSelecionado().setCliente(getClienteSelecionado());
        setOrcamentosResult(orcamentoService.findByCliente(getClienteSelecionado()));
        setParcelasResult(financeiroService.findParcelasByCliente(getClienteSelecionado()));
    }

    public String criaOrcamentoComAnaminese(Anaminese anaminese) {
        cleanCache();
        getOrcamentoSelecionado().setAnaminese(anaminese);
        return "/pages/adicionarOrcamento.faces";
    }

    public OrcamentoItem getOrcamentoItemSelecionado() {
        return orcamentoItemSelecionado;
    }

    public void setOrcamentoItemSelecionado(OrcamentoItem orcamentoItemSelecionado) {
        this.orcamentoItemSelecionado = orcamentoItemSelecionado;
    }

    //fixme remover a chamada direta ao banco
    public List<Servico> getServicos() {
        return servicosService.findAll();
    }

    public List<Usuario> getDentistas() {
        return usuarioService.findAllDentistas();
    }

    public List<TipoPagamento> getTiposPagamento() {
        return Arrays.asList(TipoPagamento.values());
    }

    public String iniciarCadastroOrcamentoItem() {
        orcamentoItemSelecionado = new OrcamentoItem(orcamentoSelecionado);
        return "/pages/adicionarOrcamentoItem.faces";
    }

    public String removerOrcamento() {
        orcamentoService.removeOrcamento(orcamentoSelecionado);
        cleanCache();
        return "/pages/atendimento.faces";
    }

    public String removerItem() {
        orcamentoSelecionado.getOrcamentoItens().remove(orcamentoItemSelecionado);
        orcamentoSelecionado.recalcularValoresItens();
        return "/pages/adicionarOrcamento.faces";
    }

    public String finalizarCadastroOrcamentoItem() {
        orcamentoItemSelecionado.recalcularTotal();
        orcamentoSelecionado.getOrcamentoItens().add(orcamentoItemSelecionado);
        orcamentoSelecionado.recalcularValoresItens();
        return "/pages/adicionarOrcamento.faces";
    }

    public String finalizarCadastroOrcamento() {
        if (orcamentoSelecionado.getTipoPagamento() != TipoPagamento.CREDITO) {
            orcamentoSelecionado.setNumeroParcelas(1);
        }
        if (orcamentoSelecionado.getTipoPagamento() == TipoPagamento.CREDITO) {
            if (orcamentoSelecionado.getNumeroParcelas() == null || orcamentoSelecionado.getNumeroParcelas() <= 0) {
                JsfUtils.addError("Informar a quantidade de parcelas");
                return "/pages/adicionarOrcamento.faces";
            }
        }
        orcamentoSelecionado.setCliente(clienteSelecionado);
        orcamentoService.addOrcamento(orcamentoSelecionado);
        cleanCache();
        return "/pages/atendimento.faces";
    }

    public String finalizarEdicao() {
        orcamentoService.updateOrcamento(orcamentoSelecionado);
        cleanCache();
        return "/pages/atendimento.faces";
    }

    public List<Parcela> getParcelasResult() {
        return parcelasResult;
    }

    private void setParcelasResult(List<Parcela> parcelasResult) {
        this.parcelasResult = parcelasResult;
    }

    public String pagarParcela() {
        financeiroService.setPagamentoParcela(getParcelaSelecionada());
        cleanCache();
        return "/pages/atendimento.faces";
    }

    public Parcela getParcelaSelecionada() {
        return parcelaSelecionada;
    }

    public void setParcelaSelecionada(Parcela parcelaSelecionada) {
        this.parcelaSelecionada = parcelaSelecionada;
    }

    public String gerarBoleto() throws JRException, IOException {
        byte[] boleto = financeiroService.gerarBoleto(getParcelaSelecionada());
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseContentLength(boleto.length);
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"parcela_"
                + getParcelaSelecionada().getNumero() + "_"
                + getParcelaSelecionada().getOrcamento().getCliente().getId() + ".pdf\"");
        OutputStream output = ec.getResponseOutputStream();
        output.write(boleto);
        fc.responseComplete();
        return null;
    }

}
