package br.com.devmedia.consultorioee.controller;

import br.com.devmedia.consultorioee.model.Servico;
import br.com.devmedia.consultorioee.service.ServicosService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vsaueia
 */
@Named
@SessionScoped
public class ServicosController extends BasicController implements Serializable {

    private final String ERRO_SERVICO_UK = "Já existe um serviço com essa descrição";
    @EJB
    private ServicosService servicosService;
    
    private String filtroDescricao;
    private List<Servico> servicosResult = new ArrayList<>();

    private Servico servicoSelecionado = new Servico();

    public String getFiltroDescricao() {
        return filtroDescricao;
    }

    public void setFiltroDescricao(String filtroDescricao) {
        this.filtroDescricao = filtroDescricao;
    }

    public List<Servico> getServicosResult() {
        if(servicosResult.isEmpty()) {
            setServicosResult(servicosService.findAll());
        }
        return servicosResult;
    }

    public void setServicosResult(List<Servico> servicosResult) {
        this.servicosResult = servicosResult;
    }

    public Servico getServicoSelecionado() {
        return servicoSelecionado;
    }

    public void setServicoSelecionado(Servico servicoSelecionado) {
        this.servicoSelecionado = servicoSelecionado;
    }
    
    public String buscar() {
       servicosResult = servicosService.findByDescricao(filtroDescricao);
       return "servicos.faces";
    }
    
    public String startAdicionarServico() {
        servicoSelecionado = new Servico();
        return "/pages/adicionarServico.faces";
    }

    public String finalizarCadastro() {
        if (existsViolations(servicoSelecionado)) {
            return "/pages/adicionarServico.faces";
        }

        servicosService.addServico(servicoSelecionado);
        return cancelar();
    }
    
    public String cancelar() {
        servicoSelecionado = null;      
        servicosResult = new ArrayList<>();
        return "/pages/servicos.faces";
    }
    
    public String editarServico() {
        return "/pages/editarServico.faces";
    }
    
    public String finalizarEdicao() {
        if (existsViolations(servicoSelecionado)) {
            servicoSelecionado = servicosService.findById(servicoSelecionado.getId());
            return "/pages/editarServico.faces";
        }

        servicosService.updateServico(servicoSelecionado);
        return cancelar();
    }
    
    public String removerServico() {
        servicosService.removeServico(servicoSelecionado);
        return cancelar();
    }

}
