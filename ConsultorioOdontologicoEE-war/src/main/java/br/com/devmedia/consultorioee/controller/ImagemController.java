package br.com.devmedia.consultorioee.controller;


import br.com.devmedia.consultorioee.model.CategoriaImagem;
import br.com.devmedia.consultorioee.model.Imagem;
import br.com.devmedia.consultorioee.model.Orcamento;
import br.com.devmedia.consultorioee.service.CategoriaImagemService;
import br.com.devmedia.consultorioee.service.ImagemService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Named
public class ImagemController extends BasicController implements java.io.Serializable {

    @EJB
    private ImagemService imagemService;

    @EJB
    private CategoriaImagemService categoriaImagemService;

    private List<Imagem> imagensResult = new ArrayList<>();
    private List<CategoriaImagem> categoriasDeImagem = new ArrayList<>();

    private Imagem imagemSelecionada;
    private Orcamento orcamentoSelecionado;
    private CategoriaImagem categoriaImagemSelecionada;

    private Part arquivoImagem;

    public List<Imagem> getImagensResult() {
        return imagensResult;
    }

    public Part getArquivoImagem() {
        return arquivoImagem;
    }

    public void setArquivoImagem(Part arquivoImagem) {
        this.arquivoImagem = arquivoImagem;
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

    public String visualizarImagens() {
        return "/pages/viewImagem.faces";
    }

    public CategoriaImagem getCategoriaImagemSelecionada() {
        return categoriaImagemSelecionada;
    }

    public void setCategoriaImagemSelecionada(CategoriaImagem categoriaImagemSelecionada) {
        this.categoriaImagemSelecionada = categoriaImagemSelecionada;
    }

    public List<CategoriaImagem> getCategoriasDeImagem() {
        if(categoriasDeImagem.isEmpty()) {
            categoriasDeImagem = categoriaImagemService.findAll();
        }
        return categoriasDeImagem;
    }

    public String iniciarCadastroDeImagem() {
        imagemSelecionada = new Imagem();
        return "/pages/adicionarImagem.faces";
    }

    public String finalizarCadastroDeImagem() throws IOException {
        imagemSelecionada.setOrcamento(orcamentoSelecionado);
        byte[] imagemEmBytes = new byte[(int) arquivoImagem.getSize()];
        // nao é seguro
        arquivoImagem.getInputStream().read(imagemEmBytes);
        imagemSelecionada.setImagem(imagemEmBytes);
        imagemService.addImagem(imagemSelecionada);
        return "/pages/viewImagem.faces";
    }
}
