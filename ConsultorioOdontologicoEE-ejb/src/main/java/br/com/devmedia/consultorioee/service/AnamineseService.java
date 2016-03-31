package br.com.devmedia.consultorioee.service;

import br.com.devmedia.consultorioee.model.Anaminese;
import br.com.devmedia.consultorioee.model.Cliente;
import br.com.devmedia.consultorioee.model.Orcamento;
import br.com.devmedia.consultorioee.service.repository.AnamineseRepository;

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
public class AnamineseService extends BasicService {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;
    private AnamineseRepository anamineseRepository;
    

    @PostActivate
    @PostConstruct
    private void postConstruct() {
        anamineseRepository = new AnamineseRepository(entityManager);
    }
    
    public Anaminese findById(Long id) {
        return anamineseRepository.findById(id);
    }
    
    public Anaminese addAnaminese(Anaminese anaminese) {
        return anamineseRepository.addAnaminese(anaminese);
    }
    
    public Anaminese updateAnaminese(Anaminese anaminese) {
        return anamineseRepository.updateAnaminese(anaminese);
    }
    
    public void removeAnaminese(Anaminese anaminese) {
        anamineseRepository.removeAnaminese(anaminese);
    }
    
    public List<Anaminese> findByCliente(Cliente cliente) {
        return anamineseRepository.findByCliente(cliente);
    }

    public List<Anaminese> findByOrcamento(Orcamento orcamento) {
        return anamineseRepository.findByOrcamento(orcamento);
    }
}
