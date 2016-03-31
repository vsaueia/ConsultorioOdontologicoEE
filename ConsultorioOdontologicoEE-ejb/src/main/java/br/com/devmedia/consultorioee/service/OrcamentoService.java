package br.com.devmedia.consultorioee.service;

import br.com.devmedia.consultorioee.model.Cliente;
import br.com.devmedia.consultorioee.model.Orcamento;
import br.com.devmedia.consultorioee.model.OrcamentoItem;
import br.com.devmedia.consultorioee.service.repository.OrcamentoRepository;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author vsaueia
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class OrcamentoService extends BasicService {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    private OrcamentoRepository orcamentoRepository;

    @EJB
    private FinanceiroService financeiroService;

    @PostActivate
    @PostConstruct
    private void build() {
        orcamentoRepository = new OrcamentoRepository(entityManager);
    }

    public Orcamento addOrcamento(Orcamento orcamento) {
        Orcamento orcamentoAtualizado = orcamentoRepository.addOrcamento(orcamento);
        financeiroService.constroiParcelas(orcamento);
        return orcamentoAtualizado;
    }

    public Orcamento updateOrcamento(Orcamento orcamento) {
        financeiroService.limparParcelas(orcamento);
        Orcamento orcamentoAtualizado = orcamentoRepository.updateOrcamento(orcamento);
        financeiroService.constroiParcelas(orcamentoAtualizado);
        return orcamentoAtualizado;
    }

    public Orcamento findById(Long orcamentoId) {
        return orcamentoRepository.findById(orcamentoId);
    }

    public void removeOrcamento(Orcamento orcamento) {
        orcamentoRepository.removeOrcamento(orcamento);
    }

    public OrcamentoItem addItem(OrcamentoItem item) {
        return orcamentoRepository.addItem(item);
    }

    public OrcamentoItem setItem(OrcamentoItem item) {
        return orcamentoRepository.updateItem(item);
    }

    public void removeItem(OrcamentoItem item) {
        orcamentoRepository.removeItem(item);
    }

    public OrcamentoItem findItemById(Long orcamentoItemId) {
        return orcamentoRepository.findOrcamentoItemById(orcamentoItemId);
    }

    public List<Orcamento> findByCliente(Cliente cliente) {
        return orcamentoRepository.findByCliente(cliente);
    }

    public List<OrcamentoItem> findItensByOrcamentoId(Long orcamentoId) {
        return orcamentoRepository.findItensByOrcamentoId(orcamentoId);
    }

    public List<Orcamento> findByClienteId(Long clienteId) {
        return orcamentoRepository.findByClienteId(clienteId);
    }
}
