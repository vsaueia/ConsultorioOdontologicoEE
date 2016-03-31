/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.devmedia.consultorioee.controller;

import br.com.devmedia.consultorioee.model.Anaminese;
import br.com.devmedia.consultorioee.model.Cliente;
import br.com.devmedia.consultorioee.service.AnamineseService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.List;

/**
 *
 * @author Dyego Souza do Carmo
 * @version 1.0
 * @since 08/2014
 */
@Named
@SessionScoped
public class AnamineseController extends BasicController implements java.io.Serializable {
    
    @EJB
    private AnamineseService anamineseService;

    private Anaminese anamineseSelecionada;
    private Cliente clienteSelecionado;
    private List<Anaminese> anaminesesResult;

    public Anaminese getAnamineseSelecionada() {
        return anamineseSelecionada;
    }

    public void setAnamineseSelecionada(Anaminese anamineseSelecionada) {
        this.anamineseSelecionada = anamineseSelecionada;
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public List<Anaminese> getAnaminesesResult() {
        return anaminesesResult;
    }

    public void setAnaminesesResult(List<Anaminese> anaminesesResult) {
        this.anaminesesResult = anaminesesResult;
    }
    
    public void cleanCache() {
        setAnamineseSelecionada(new Anaminese());
        getAnamineseSelecionada().setCliente(clienteSelecionado);
        anaminesesResult = anamineseService.findByCliente(clienteSelecionado);
    }
    
    public String iniciarCadastro() {
        cleanCache();
        return "/pages/adicionarAnaminese.faces";
    }
    
    public String finalizarCadastro() {
        anamineseSelecionada.setCliente(clienteSelecionado);
        anamineseService.addAnaminese(anamineseSelecionada);
        cleanCache();
        return "/pages/atendimento.faces";
    }
    
    public String removerAnaminese() {
        anamineseService.removeAnaminese(anamineseSelecionada);
        cleanCache();
        return "/pages/atendimento.faces";
    }
    
    public String editarAnaminese() {
        return "/pages/adicionarAnaminese.faces";
    }
    
    public String finalizarEdicao() {
        anamineseService.updateAnaminese(anamineseSelecionada);
        cleanCache();
        return "/pages/atendimento.faces";
    }
    

}
