package br.com.devmedia.consultorioee.service;

import br.com.devmedia.consultorioee.model.Imagem;
import br.com.devmedia.consultorioee.model.Orcamento;
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

    public Imagem updateImagem(Imagem servico) {
        return imagemRepository.updateImagem(servico);
    }

    public void removeImagem(Imagem servico){
        imagemRepository.removeImagem(servico);
    }

    public Imagem addImagem(Imagem servico) {
        return imagemRepository.addImagem(servico);
    }

    public List<Imagem> findAll() {
        return imagemRepository.findAll();
    }

    public List<Imagem> findByOrcamento(Orcamento orcamento) {
        return imagemRepository.findByOrcamento(orcamento);
    }
}
