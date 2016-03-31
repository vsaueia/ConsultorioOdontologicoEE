package br.com.devmedia.consultorioee.controller;

import br.com.devmedia.consultorioee.model.Cliente;
import br.com.devmedia.consultorioee.service.ClienteService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vsaueia
 */
@Named
@SessionScoped
public class ClienteController extends BasicController implements Serializable {

    @EJB
    private ClienteService clienteService;

    private String filtroNome;
    private List<Cliente> clientesResult = new ArrayList<>();

    private Cliente clienteSelecionado = new Cliente();

    public String getFiltroNome() {
        return filtroNome;
    }

    public void setFiltroNome(String filtroNome) {
        this.filtroNome = filtroNome;
    }

    public List<Cliente> getClientesResult() {
        if (clientesResult.isEmpty()) {
            setClientesResult(clienteService.findAll());
        }
        return clientesResult;
    }

    private void setClientesResult(List<Cliente> clientesResult) {
        this.clientesResult = clientesResult;
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public String buscar() {
        clientesResult = clienteService.findByNome(filtroNome);
        return "clientes.faces";
    }

    public String startAdicionarCliente() {
        clienteSelecionado = new Cliente();
        return "/pages/adicionarCliente.faces";
    }

    public String finalizarCadastro() {
        if (existsViolations(clienteSelecionado)) {
            return "/pages/adicionarCliente.faces";
        }

        clienteService.addCliente(clienteSelecionado);
        return cancelar();
    }

    public String cancelar() {
        clienteSelecionado = null;
        clientesResult = new ArrayList<>();
        return "/pages/clientes.faces";
    }

    public String editarCliente() {
        return "/pages/adicionarCliente.faces";
    }

    public String finalizarEdicao() {
        if (existsViolations(clienteSelecionado)) {
            return "/pages/editarCliente.faces";
        }

        clienteService.updateCliente(clienteSelecionado);
        return cancelar();
    }

    public String removerCliente() {
        clienteService.removeCliente(clienteSelecionado);
        return cancelar();
    }

    public long getQuantidadeClientes() {
        return clienteService.countAll();
    }

}
