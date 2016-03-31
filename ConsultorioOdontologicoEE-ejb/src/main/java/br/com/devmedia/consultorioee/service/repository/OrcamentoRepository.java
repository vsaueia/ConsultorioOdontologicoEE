/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devmedia.consultorioee.service.repository;

import br.com.devmedia.consultorioee.model.Cliente;
import br.com.devmedia.consultorioee.model.Orcamento;
import br.com.devmedia.consultorioee.model.OrcamentoItem;

import javax.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author vsaueia
 */
public class OrcamentoRepository extends BasicRepository {
    
    public OrcamentoRepository(EntityManager entityManger) {
        super(entityManger);
    }
    
    public Orcamento findById(Long orcamentoId) {
        return getEntity(Orcamento.class, orcamentoId);
    }
    
    public Orcamento addOrcamento(Orcamento orcamento) {
        return addEntity(Orcamento.class, orcamento);
    }
    
    public Orcamento updateOrcamento(Orcamento orcamento) {
        return updateEntity(Orcamento.class, orcamento);
    }
    
    public void removeOrcamento(Orcamento orcamento) {
        removeEntity(Orcamento.class, orcamento);
    }
    
    public List<Orcamento> findByCliente(Cliente cliente){
        return getEntityManager().createNamedQuery("Orcamento.findByCliente", Orcamento.class)
                .setParameter("cliente", cliente)
                .getResultList();
    }
    
    public List<OrcamentoItem> findItensByOrcamentoId(Long orcamentoId) {
        return getEntityManager().createNamedQuery("OrcamentoItem.findItensByOrcamentoId", OrcamentoItem.class)
                .setParameter("orcamentoId", orcamentoId)
                .getResultList();
    }
    
    public OrcamentoItem findOrcamentoItemById(Long orcamentoItemId) {
        return getEntity(OrcamentoItem.class, orcamentoItemId);
    }
    
    public OrcamentoItem addItem(OrcamentoItem orcamentoItem) {
        return addEntity(OrcamentoItem.class, orcamentoItem);
    }
    
    public OrcamentoItem updateItem(OrcamentoItem orcamentoItem) {
        return updateEntity(OrcamentoItem.class, orcamentoItem);
    }
    
    public void removeItem(OrcamentoItem orcamentoItem) {
        removeEntity(OrcamentoItem.class, orcamentoItem);
    }

    public List<Orcamento> findByClienteId(Long clienteId) {
        return getEntityManager().createNamedQuery("Orcamento.findByClienteId", Orcamento.class)
                .setParameter("clienteId", clienteId)
                .getResultList();
    }
      
}
