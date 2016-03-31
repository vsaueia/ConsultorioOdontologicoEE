package br.com.devmedia.consultorioee.service;

import br.com.devmedia.consultorioee.model.Cliente;
import br.com.devmedia.consultorioee.model.Parcela;
import br.com.devmedia.consultorioee.util.FormatadoresUtil;
import net.sf.jasperreports.engine.JRException;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vsaueia
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class MailService extends BasicService {

    private static final long serialVersionUID = 1L;

    @EJB
    private FinanceiroService financeiroService;

    @Resource(mappedName = "mail/gmail")
    private Session mailSession;

    @EJB
    private ClienteService clienteService;

    @Schedule(hour = "*", minute = "28, 29", persistent = false)
    public void enviaBoletosPorEmail() throws JRException, IOException {
        System.out.println("Starting enviaBoletosPorEmail()");
        List<Cliente> clientes = clienteService.findByNome("%");
        for (Cliente cliente : clientes) {
            List<Parcela> parcelas = financeiroService.findParcelasClienteEmAberto(cliente);
            for (Parcela parcela : parcelas) {
                sendEmailTo(cliente, parcela);
            }
        }
    }

    private void sendEmailTo(Cliente cliente, Parcela parcela) throws JRException, IOException {
        System.out.println("Chegou a solicitacao para " + cliente.getNome() + " da parcela " + parcela.getNumero());
        byte[] pdfBoleto = financeiroService.gerarBoleto(parcela);
        InputStream stream = getClass().getResourceAsStream("invoice.html");
        byte[] invoiceBytes = new byte[stream.available()];
        stream.read(invoiceBytes);
        stream.close();
        String body = new String(invoiceBytes);
        body = body.replaceAll("@@@NOME_CLIENTE@@@", cliente.getNome());
        body = body.replaceAll("@@@PARCELA_NUMERO@@@", String.valueOf(parcela.getNumero()));
        body = body.replaceAll("@@@PARCELA_DATA@@@", FormatadoresUtil.formatarData(new Date()));
        body = body.replaceAll("@@@PARCELA_VALOR@@@", FormatadoresUtil.formatarDecimal(parcela.getValor()));
        body = body.replaceAll("@@@NOME_USUARIO@@@", parcela.getOrcamento().getDentista().getNome());

        sendBoletoMail(body, pdfBoleto, cliente);
    }

    private void sendBoletoMail(String body, byte[] pdfBoleto, Cliente cliente) {
        try {
            Multipart multipart = new MimeMultipart();;
            Message msg = new MimeMessage(mailSession);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(cliente.getEmail()));
            msg.setFrom(new InternetAddress("consultorioEEDevmedia@gmail.com"));

            msg.setSubject("[ConsultorioEE] Invoice para pagamento referente a "
                    + "consulta Odontologica enviado em "
                    + FormatadoresUtil.formatarData(new Date()) + " [/ConsultorioEE]");
            // The Message
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html; charset=ISO-8859-1");
            multipart.addBodyPart(messageBodyPart);
            // The PDF File
            BodyPart boletoBodyPart = new MimeBodyPart();
            boletoBodyPart.setFileName("boleto.pdf");
            boletoBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(
                    pdfBoleto, "application/pdf")));
            multipart.addBodyPart(boletoBodyPart);
            // Attach the Multipart Data
            msg.setContent(multipart);
            Transport.send(msg);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            System.out.println("[FinanceService] Impossivel enviar email para "
                    + cliente.getNome() + " pelo email " + cliente.getEmail() + " - "
                    + ex.getMessage());
        }
    }

}
