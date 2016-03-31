/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devmedia.consultorioee.service.repository;

import br.com.devmedia.consultorioee.model.Servico;

import javax.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author vsaueia
 */
public class ServicoRepository extends BasicRepository{
    private static final long serialVersionUID = 1L;
    
    public ServicoRepository(EntityManager entityManger) {
        super(entityManger);
    }
    
    public Servico addServico (Servico servico) {
        return addEntity(Servico.class, servico);
    }
    
    public Servico updateServico (Servico servico) {
        return updateEntity(Servico.class, servico);
    }
    
    public void removeServico (Servico servico) {
        removeEntity(Servico.class, servico);
    }
    
    public Servico findById (Long id) {
        return getEntity(Servico.class, id);
    }
    
    public List<Servico> findAll() {
        return getEntityManager().createNamedQuery("Servico.findAll")
                .getResultList();
    }
    
    public List<Servico> findByDescricao(String descricao) {
        return getEntityManager()
                .createNamedQuery("Servico.findByDescricao", Servico.class)
                .setParameter("descricao", descricao + LIKE_CHAR)
                .getResultList();
    }

    public Servico findByDescricaoExata(String descricao) {
        return uniqueResultOrNull(getEntityManager()
                .createNamedQuery("Servico.findByDescricaoExata", Servico.class)
                .setParameter("descricao", descricao));
    }

    public long countServicosCadastrados() {
        return uniqueResultOrNull(getEntityManager().createNamedQuery("Servico.countServicos", Long.class));
    }
}
