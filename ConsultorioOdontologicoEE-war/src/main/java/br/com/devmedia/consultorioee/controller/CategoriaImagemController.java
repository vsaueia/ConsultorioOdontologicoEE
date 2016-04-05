package br.com.devmedia.consultorioee.controller;

import br.com.devmedia.consultorioee.model.CategoriaImagem;
import br.com.devmedia.consultorioee.service.CategoriaImagemService;

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
public class CategoriaImagemController extends BasicController implements Serializable {

    private final String ERRO_CATEGORIA_UK = "Já existe uma categoria com essa descrição";
    @EJB
    private CategoriaImagemService categoriaImagemService;
    
    private String filtroDescricao;
    private List<CategoriaImagem> categoriasDeImagem = new ArrayList<>();

    private CategoriaImagem categoriaImagemSelecionada = new CategoriaImagem();

    public String getFiltroDescricao() {
        return filtroDescricao;
    }

    public void setFiltroDescricao(String filtroDescricao) {
        this.filtroDescricao = filtroDescricao;
    }

    public List<CategoriaImagem> getCategoriasDeImagem() {
        if(categoriasDeImagem.isEmpty()) {
            setCategoriasDeImagem(categoriaImagemService.findAll());
        }
        return categoriasDeImagem;
    }

    public void setCategoriasDeImagem(List<CategoriaImagem> categoriasDeImagem) {
        this.categoriasDeImagem = categoriasDeImagem;
    }

    public CategoriaImagem getCategoriaImagemSelecionada() {
        return categoriaImagemSelecionada;
    }

    public void setCategoriaImagemSelecionada(CategoriaImagem categoriaImagemSelecionada) {
        this.categoriaImagemSelecionada = categoriaImagemSelecionada;
    }

    public String buscar() {
       categoriasDeImagem = categoriaImagemService.findByDescricao(filtroDescricao);
       return "categoriasDeImagem.faces";
    }
    
    public String startAdicionarCategoria() {
        categoriaImagemSelecionada = new CategoriaImagem();
        return "/pages/adicionarCategoriaDeImagem.faces";
    }

    public String finalizarCadastro() {
        if (existsViolations(categoriaImagemSelecionada)) {
            return "/pages/adicionarCategoriaDeImagem.faces";
        }

        categoriaImagemService.addCategoriaImagem(categoriaImagemSelecionada);
        return cancelar();
    }
    
    public String cancelar() {
        categoriaImagemSelecionada = null;
        categoriasDeImagem = new ArrayList<>();
        return "/pages/categoriasDeImagem.faces";
    }
    
    public String editarCategoriaDeImagem() {
        return "/pages/adicionarCategoriasDeImagem.faces";
    }
    
    public String finalizarEdicao() {
        if (existsViolations(categoriaImagemSelecionada)) {
            categoriaImagemSelecionada = categoriaImagemService.findById(categoriaImagemSelecionada.getId());
            return "/pages/adicionarCategoriasDeImagem.faces";
        }

        categoriaImagemService.updateCategoriaImagem(categoriaImagemSelecionada);
        return cancelar();
    }
    
    public String removerCategoriaDeImagem() {
        categoriaImagemService.removeCategoriaImagem(categoriaImagemSelecionada);
        return cancelar();
    }

    public Long getQuantidadeDeCategoriasDeImagem() {
        return categoriaImagemService.contarCategoriasDeImagensCadastradas();
    }
}
