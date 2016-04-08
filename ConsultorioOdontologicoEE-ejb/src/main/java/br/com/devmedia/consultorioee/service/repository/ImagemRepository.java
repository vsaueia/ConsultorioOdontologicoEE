package br.com.devmedia.consultorioee.service.repository;

import br.com.devmedia.consultorioee.model.Imagem;

import javax.persistence.EntityManager;
import java.util.List;

public class ImagemRepository extends BasicRepository {
    
    public ImagemRepository(EntityManager entityManger) {
        super(entityManger);
    }

    public Imagem addImagem (Imagem imagem) {
        return addEntity(Imagem.class, imagem);
    }

    public Imagem updateImagem (Imagem imagem) {
        return updateEntity(Imagem.class, imagem);
    }

    public void removeImagem (Imagem imagem) {
        removeEntity(Imagem.class, imagem);
    }

    public Imagem findById (Long id) {
        return getEntity(Imagem.class, id);
    }

    public List<Imagem> findAll() {
        return getEntityManager().createNamedQuery("Imagem.findAll")
                .getResultList();
    }

    public List<Imagem> findByOrcamentoIdECategoriaId(Long orcamentoId, Long categoriaId) {
        return getEntityManager().createNamedQuery("Imagem.findByOrcamentoIdECategoriaId", Imagem.class)
                .setParameter("orcamentoId", orcamentoId)
                .setParameter("categoriaId", categoriaId)
                .getResultList();
    }
}
