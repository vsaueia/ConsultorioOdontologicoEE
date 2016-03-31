/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.devmedia.consultorioee.service.repository;

import br.com.devmedia.consultorioee.model.Cliente;
import br.com.devmedia.consultorioee.model.Orcamento;
import br.com.devmedia.consultorioee.model.Parcela;

import javax.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author vsaueia
 */
public class FinanceiroRepository extends BasicRepository {
    private static final long serialVersionUID = 1L;
    
    public FinanceiroRepository(EntityManager entityManger) {
        super(entityManger);
    }
    
    public Parcela findById(Long parcelaId) {
        return getEntity(Parcela.class, parcelaId);
    }
    
    public Parcela addParcela(Parcela parcela) {
        return addEntity(Parcela.class, parcela);
    }
    
    public Parcela updateParcela(Parcela parcela) {
        return updateEntity(Parcela.class, parcela);
    }
    
    public void removeParcela(Parcela parcela) {
        removeEntity(Parcela.class, parcela);
    }
    
    public List<Parcela> findParcelasByOrcamento(Orcamento orcamento) {
        return getEntityManager().createNamedQuery("Parcela.findByOrcamento", Parcela.class)
                .setParameter("orcamento", orcamento)
                .getResultList();
    }
    
    public List<Parcela> findParcelasOrcamentoEmAberto(Orcamento orcamento) {
        return getEntityManager().createNamedQuery("Parcela.findByOrcamentoAndStatusPagamento", Parcela.class)
                .setParameter("orcamento", orcamento)
                .setParameter("status", false)
                .getResultList();
    }
    
    public List<Parcela> findParcelasOrcamentoPagas(Orcamento orcamento) {
        return getEntityManager().createNamedQuery("Parcela.findByOrcamentoAndStatusPagamento", Parcela.class)
                .setParameter("orcamento", orcamento)
                .setParameter("status", true)
                .getResultList();
    }
    
    public List<Parcela> findParcelasByCliente(Cliente cliente) {
        return getEntityManager().createNamedQuery("Parcela.findByCliente", Parcela.class)
                .setParameter("cliente", cliente)
                .getResultList();
    }
    
    public List<Parcela> findParcelasClienteEmAberto(Cliente cliente) {
        return getEntityManager().createNamedQuery("Parcela.findByClienteAndStatusPagamento", Parcela.class)
                .setParameter("cliente", cliente)
                .setParameter("status", false)
                .getResultList();
    }
    
    public List<Parcela> findParcelasClientePagas(Cliente cliente) {
        return getEntityManager().createNamedQuery("Parcela.findByClienteAndStatusPagamento", Parcela.class)
                .setParameter("cliente", cliente)
                .setParameter("status", true)
                .getResultList();
    }
    
    public Parcela setPagamentoParcela(Parcela parcela) {
        parcela.setPago(true);
        return updateParcela(parcela);
    }

    public void deleteParcelas(Orcamento orcamento) {
        getEntityManager().createNamedQuery("Parcela.deleteByOrcamento")
                .setParameter("orcamento", orcamento)
                .executeUpdate();
    }

}
