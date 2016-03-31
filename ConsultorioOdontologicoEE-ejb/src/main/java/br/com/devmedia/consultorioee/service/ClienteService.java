package br.com.devmedia.consultorioee.service;

import br.com.devmedia.consultorioee.model.Cliente;
import br.com.devmedia.consultorioee.service.repository.ClienteRepository;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 *
 * @author vsaueia
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClienteService extends BasicService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private ClienteRepository clienteRepository;
    
    @PostConstruct
    @PostActivate
    private void build() {
        clienteRepository = new ClienteRepository(entityManager);
    }
    
    public Cliente findById(Long id) {
        return clienteRepository.findById(id);
    }
    
    public Cliente updateCliente(Cliente cliente) {
        return clienteRepository.updateCliente(cliente);
    }
    
    @Transactional(Transactional.TxType.REQUIRED)
    public void removeCliente(Cliente cliente){
        clienteRepository.removeCliente(cliente);
    }
    
    public Cliente addCliente(Cliente cliente) {
        return clienteRepository.addCliente(cliente);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    
    public List<Cliente> findByNome(String nome) {
        return clienteRepository.findByNome(nome);
    }
    
    public List<Cliente> findClientesComPagamentoAberto() {
        return clienteRepository.findClientesComPagamentoAberto();
    }
    
    public List<Cliente> findClienteComOrcamentoByData(int ano, int mes) {
        return clienteRepository.findClienteComOrcamentoByData(mes, ano);
    }

    public long countAll() {
        return clienteRepository.countAll();
    }

    
}
