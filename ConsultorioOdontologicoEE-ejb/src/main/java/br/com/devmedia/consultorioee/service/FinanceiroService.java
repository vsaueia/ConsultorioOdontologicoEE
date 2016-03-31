package br.com.devmedia.consultorioee.service;

import br.com.devmedia.consultorioee.builder.ParcelaBuilder;
import br.com.devmedia.consultorioee.model.Cliente;
import br.com.devmedia.consultorioee.model.Orcamento;
import br.com.devmedia.consultorioee.model.Parcela;
import br.com.devmedia.consultorioee.service.repository.FinanceiroRepository;
import net.sf.jasperreports.engine.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vsaueia
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class FinanceiroService extends BasicService {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;
    private FinanceiroRepository financeiroRepository;

    @Resource(mappedName = "mail/gmail")
    private Session mailSession;

    @EJB
    private ClienteService clienteService;

    @PostActivate
    @PostConstruct
    private void postConstruct() {
        financeiroRepository = new FinanceiroRepository(entityManager);
    }

    public Parcela addParcela(Parcela parcela) {
        return financeiroRepository.addParcela(parcela);
    }

    public Parcela findById(Long id) {
        return financeiroRepository.findById(id);
    }

    public Parcela setParcela(Parcela parcela) {
        return financeiroRepository.updateParcela(parcela);
    }

    public void removeParcela(Parcela parcela) {
        financeiroRepository.removeParcela(parcela);
    }

    public List<Parcela> findParcelasByOrcamento(Orcamento orcamento) {
        return financeiroRepository.findParcelasByOrcamento(orcamento);
    }

    public List<Parcela> findParcelasOrcamentoPagas(Orcamento orcamento) {
        return financeiroRepository.findParcelasOrcamentoPagas(orcamento);
    }

    public List<Parcela> findParcelasOrcamentoEmAberto(Orcamento orcamento) {
        return financeiroRepository.findParcelasOrcamentoEmAberto(orcamento);
    }

    public List<Parcela> findParcelasByCliente(Cliente cliente) {
        return financeiroRepository.findParcelasByCliente(cliente);
    }

    public List<Parcela> findParcelasClientePagas(Cliente cliente) {
        return financeiroRepository.findParcelasClientePagas(cliente);
    }

    public List<Parcela> findParcelasClienteEmAberto(Cliente cliente) {
        return financeiroRepository.findParcelasClienteEmAberto(cliente);
    }

    public Parcela setPagamentoParcela(Parcela parcela) {
        return financeiroRepository.setPagamentoParcela(parcela);
    }

    public void constroiParcelas(Orcamento orcamento) {
        BigDecimal valorParcela = orcamento.getTotal()
                .divide(new BigDecimal(orcamento.getNumeroParcelas()), RoundingMode.CEILING);

        if (orcamento.getNumeroParcelas() > 1) {
            for (int i = 0; i < orcamento.getNumeroParcelas(); i++) {
                Parcela parcela = new ParcelaBuilder().comNumero(i + 1).comOrcamento(orcamento)
                        .comValor(valorParcela)
                        .emAberto(true)
                        .create();
                if (orcamento.getNumeroParcelas() == (i + 1)) {
                    BigDecimal ultimaParcela = valorParcela.multiply(new BigDecimal(i));
                    ultimaParcela = orcamento.getTotal().subtract(ultimaParcela);
                    parcela.setValor(ultimaParcela);
                }
                addParcela(parcela);
            }
        }
    }

    public void limparParcelas(Orcamento orcamento) {
        financeiroRepository.deleteParcelas(orcamento);
    }

    public byte[] gerarBoleto(Parcela parcela) throws JRException, JRException {
        String codigoBarras = "9126731921927319287312973";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("parcela", parcela);
        parameters.put("codigobarras", codigoBarras);
        JasperReport jr = JasperCompileManager.compileReport(this.getClass().getResourceAsStream("boletoParcela.jrxml"));
        JasperPrint jp = JasperFillManager.fillReport(jr, parameters);
        byte[] toReturn = JasperExportManager.exportReportToPdf(jp);
        return toReturn;
    }

}
