package br.com.devmedia.consultorioee.controller;


import br.com.devmedia.consultorioee.model.Imagem;
import br.com.devmedia.consultorioee.service.ImagemService;

import javax.ejb.EJB;
import java.util.ArrayList;
import java.util.List;

public class ImagemController extends BasicController implements java.io.Serializable {

    @EJB
    private ImagemService imagemService;

    private List<Imagem> imagensResult = new ArrayList<>();
    private Imagem imagemSelecionada;

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
}
