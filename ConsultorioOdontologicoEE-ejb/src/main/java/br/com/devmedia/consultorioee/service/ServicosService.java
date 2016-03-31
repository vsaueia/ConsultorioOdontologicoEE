package br.com.devmedia.consultorioee.service;

import br.com.devmedia.consultorioee.model.Servico;
import br.com.devmedia.consultorioee.service.repository.ServicoRepository;

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
public class ServicosService extends BasicService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private ServicoRepository servicoRepository;
    
    @PostConstruct
    @PostActivate
    private void build() {
        servicoRepository = new ServicoRepository(entityManager);
    }
    
    public Servico findById(Long id) {
        return servicoRepository.findById(id);
    }
    
    public Servico updateServico(Servico servico) {
        return servicoRepository.updateServico(servico);
    }
    
    public void removeServico(Servico servico){
        servicoRepository.removeServico(servico);
    }
    
    public Servico addServico(Servico servico) {
        return servicoRepository.addServico(servico);
    }
    
    public List<Servico> findAll() {
        return servicoRepository.findAll();
    }
    
    public List<Servico> findByDescricao(String descricao) {
        return servicoRepository.findByDescricao(descricao);
    }

    public Servico findByDescricaoExata(String descricao) {
        return servicoRepository.findByDescricaoExata(descricao);
    }

    public long countServicosCadastrados() {
        return servicoRepository.countServicosCadastrados();
    }
}
