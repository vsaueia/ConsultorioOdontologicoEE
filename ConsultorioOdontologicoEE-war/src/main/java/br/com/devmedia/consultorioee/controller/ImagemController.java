package br.com.devmedia.consultorioee.controller;


import br.com.devmedia.consultorioee.model.Imagem;
import br.com.devmedia.consultorioee.model.Orcamento;
import br.com.devmedia.consultorioee.service.ImagemService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Named
public class ImagemController extends BasicController implements java.io.Serializable {

    @EJB
    private ImagemService imagemService;

    private List<Imagem> imagensResult = new ArrayList<>();
    private Imagem imagemSelecionada;
    private Orcamento orcamentoSelecionado;

    public List<Imagem> getImagensResult() {
        return imagensResult;
    }

    public void setImagensResult(List<Imagem> imagensResult) {
        this.imagensResult = imagensResult;
    }

    public Imagem getImagemSelecionada() {
        return imagemSelecionada;
    }

    public void setImagemSelecionada(Imagem imagemSelecionada) {
        this.imagemSelecionada = imagemSelecionada;
    }

    public Orcamento getOrcamentoSelecionado() {
        return orcamentoSelecionado;
    }

    public void setOrcamentoSelecionado(Orcamento orcamentoSelecionado) {
        this.orcamentoSelecionado = orcamentoSelecionado;
    }
}
