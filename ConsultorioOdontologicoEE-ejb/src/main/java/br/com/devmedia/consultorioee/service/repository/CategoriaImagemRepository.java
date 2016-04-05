/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devmedia.consultorioee.service.repository;

import br.com.devmedia.consultorioee.model.CategoriaImagem;

import javax.persistence.EntityManager;
import java.util.List;

public class CategoriaImagemRepository extends BasicRepository {

    public CategoriaImagemRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public CategoriaImagem addCategoriaImagem(CategoriaImagem categoriaImagem) {
        return addEntity(CategoriaImagem.class, categoriaImagem);
    }

    public CategoriaImagem updateCategoriaImagem(CategoriaImagem categoriaImagem) {
        return updateEntity(CategoriaImagem.class, categoriaImagem);
    }


    public void removeCategoriaImagem(CategoriaImagem categoriaImagem) {
        removeEntity(CategoriaImagem.class, categoriaImagem);
    }

    public CategoriaImagem findById(Long id) {
        return getEntity(CategoriaImagem.class, id);
    }

    public List<CategoriaImagem> findAll() {
        return getEntityManager()
                .createNamedQuery("CategoriaImagem.findAll", CategoriaImagem.class)
                .getResultList();
    }

    public List<CategoriaImagem> findByDescricao(String descricao) {
        return getEntityManager().createNamedQuery("CategoriaImagem.findByDescricao")
                .setParameter("descricao", descricao + LIKE_CHAR)
                .getResultList();
    }

    public Long countCategoriaImagemsCadastradas() {
        return uniqueResultOrElse(getEntityManager().createNamedQuery("CategoriaImagem.countAll", Long.class), 0L);
    }
}
