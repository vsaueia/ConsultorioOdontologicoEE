package br.com.devmedia.consultorioee.service;

import br.com.devmedia.consultorioee.model.Imagem;
import br.com.devmedia.consultorioee.service.repository.ImagemRepository;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ImagemService extends BasicService {
    @PersistenceContext
    private EntityManager entityManager;

    private ImagemRepository imagemRepository;

    @PostConstruct
    @PostActivate
    private void build() {
        imagemRepository = new ImagemRepository(entityManager);
    }

    public Imagem findById(Long id) {
        return imagemRepository.findById(id);
    }

    public Imagem updateImagem(Imagem imagem) {
        return imagemRepository.updateImagem(imagem);
    }

    public void removeImagem(Imagem imagem){
        imagemRepository.removeImagem(imagem);
    }

    public Imagem addImagem(Imagem imagem) {
        return imagemRepository.addImagem(imagem);
    }

    public List<Imagem> findAll() {
        return imagemRepository.findAll();
    }

    public List<Imagem> findByOrcamentoIdECategoriaId(Long orcamentoId, Long categoriaId) {
        return imagemRepository.findByOrcamentoIdECategoriaId(orcamentoId, categoriaId);
    }
}
