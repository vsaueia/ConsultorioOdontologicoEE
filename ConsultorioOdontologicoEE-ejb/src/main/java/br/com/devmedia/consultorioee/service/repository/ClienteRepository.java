package br.com.devmedia.consultorioee.service.repository;

import br.com.devmedia.consultorioee.model.Cliente;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author vsaueia
 */
public class ClienteRepository extends BasicRepository {
    
    public ClienteRepository(EntityManager entityManger) {
        super(entityManger);
    }
    
    public Cliente findById(Long id) {
        return getEntity(Cliente.class, id);
    }
    
    public Cliente addCliente(Cliente cliene) {
        return addEntity(Cliente.class, cliene);
    }
    
    public Cliente updateCliente(Cliente cliente) {
        return updateEntity(Cliente.class, cliente);
    }
        
    public List<Cliente> findByNome(String nome) {
        return getEntityManager().createNamedQuery("Cliente.findByNome", Cliente.class)
                .setParameter("nome", nome + LIKE_CHAR)
                .getResultList();
    }
    
    //exemplo de criação de hql direto
    public List<Cliente> findClientesComPagamentoAberto() {
        return getEntityManager().createQuery("select p.orcamento.cliente from Parcela p where p.pago = false")
                .getResultList();
    }
    
    public List<Cliente> findClienteComOrcamentoByData(int mes, int ano) {
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, ano);
        calendar.set(Calendar.MONTH, mes-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date dataInicial = calendar.getTime();
        
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date dataFinal = calendar.getTime();
        
        return getEntityManager().createNamedQuery("Orcamento.findClientesByData", Cliente.class)
                .setParameter("dataInicial", dataInicial)
                .setParameter("dataFinal", dataFinal)
                .getResultList();
        
    }

    public void removeCliente(Cliente cliente) {
        removeEntity(Cliente.class, cliente);
    }

    public List<Cliente> findAll() {
        return getEntityManager().createNamedQuery("Cliente.findAll").getResultList();
    }

    public long countAll() {
        return uniqueResultOrElse(getEntityManager().createNamedQuery("Cliente.countAll", Long.class), 0L);

    }
}
