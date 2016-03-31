/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devmedia.consultorioee.service.repository;

import br.com.devmedia.consultorioee.model.Anaminese;
import br.com.devmedia.consultorioee.model.Cliente;
import br.com.devmedia.consultorioee.model.Orcamento;

import javax.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author vsaueia
 */
public class AnamineseRepository extends BasicRepository {
    
    public AnamineseRepository(EntityManager entityManger) {
        super(entityManger);
    }
    
    public Anaminese findById(Long anamineseId) {
        return getEntity(Anaminese.class, anamineseId);
    }
    
    public Anaminese addAnaminese(Anaminese anaminese) {
        return addEntity(Anaminese.class, anaminese);
    }
    
    public Anaminese updateAnaminese(Anaminese anaminese) {
        return updateEntity(Anaminese.class, anaminese);
    }
    
    public void removeAnaminese(Anaminese anaminese) {
        removeEntity(Anaminese.class, anaminese);
    }
    
    public List<Anaminese> findByCliente(Cliente cliente) {
        return getEntityManager().createNamedQuery("Anaminese.findByCliente", Anaminese.class)
                .setParameter("cliente", cliente)
                .getResultList();       
    }
    
    public List<Anaminese> findByOrcamento(Orcamento orcamento) {
        return getEntityManager().createNamedQuery("Anaminese.findByOrcamento", Anaminese.class)
                .setParameter("orcamento", orcamento)
                .getResultList();       
    }
}
