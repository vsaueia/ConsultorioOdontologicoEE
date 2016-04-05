package br.com.devmedia.consultorioee.service;

import br.com.devmedia.consultorioee.model.CategoriaImagem;
import br.com.devmedia.consultorioee.service.repository.CategoriaImagemRepository;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author vsaueia
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CategoriaImagemService extends BasicService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private CategoriaImagemRepository categoriaImagemRepository;
    
    @PostConstruct
    @PostActivate
    private void build() {
        categoriaImagemRepository = new CategoriaImagemRepository(entityManager);
    }
    
    public CategoriaImagem findById(Long id) {
        return categoriaImagemRepository.findById(id);
    }
    
    public CategoriaImagem updateCategoriaImagem(CategoriaImagem categoriaImagem) {
        return categoriaImagemRepository.updateCategoriaImagem(categoriaImagem);
    }
    
    public void removeCategoriaImagem(CategoriaImagem categoriaImagem){
        categoriaImagemRepository.removeCategoriaImagem(categoriaImagem);
    }
    
    public CategoriaImagem addCategoriaImagem(CategoriaImagem categoriaImagem) {
        return categoriaImagemRepository.addCategoriaImagem(categoriaImagem);
    }
    
    public List<CategoriaImagem> findAll() {
        return categoriaImagemRepository.findAll();
    }
    
    public List<CategoriaImagem> findByDescricao(String descricao) {
        return categoriaImagemRepository.findByDescricao(descricao);
    }

    public long contarCategoriasDeImagensCadastradas() {
        return categoriaImagemRepository.countCategoriaImagemsCadastradas();
    }
}
