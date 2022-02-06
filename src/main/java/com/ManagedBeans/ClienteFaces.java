package com.ManagedBeans;

import com.Bean.Servico;
import com.DAO.ClienteDAO;
import com.Bean.Cliente;
import com.Bean.Usuario;
import com.DAO.ServicoDAO;
import com.Util.UtilFaces;
import com.DAO.UsuarioDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import com.Util.MensagensErros;
import org.primefaces.PrimeFaces;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Sergio
 */
@Named
@SessionScoped
public class ClienteFaces implements Serializable{

    private List<Cliente> clienteList = new ArrayList<>();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private Cliente clienteSelected;
    private Usuario usuario;
    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public Cliente getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Cliente clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public void doAddCliente() {
       clienteSelected = new Cliente();
    }

    public void doSaveOrUpdateCliente(){
        if(clienteSelected.getCliCodigo() == null){
            if(!clienteDAO.existeCliente(clienteSelected.getCliCPF())) {
                clienteDAO.addCliente(clienteSelected);
            }
            else{
              UtilFaces.addMensagem("Já existe um cliente cadastrado com o CPF informado", FacesMessage.SEVERITY_ERROR);
              FacesContext.getCurrentInstance().validationFailed();
            }
        }else{
            clienteSelected.setDataHoraAtualizacao(new Date());
            clienteDAO.updateCliente(clienteSelected);
        }
    }
    public void doRemoveCliente() {
        clienteDAO.removeCliente(clienteSelected);
    }

    public void populaCliente(){
        clienteList = new ArrayList<>();
        try{
            clienteList = clienteDAO.getClientes(param);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<SelectItem> getComboClientes(){
        List<SelectItem> listaComboCliente = new ArrayList<SelectItem>(0);
        List<Cliente> listaCliente = new ClienteDAO().getClientes(null);
        for (Cliente s : listaCliente) {
            SelectItem item = new SelectItem();
            item.setLabel(s.getCliNome());
            item.setValue(s);
            listaComboCliente.add(item);
        }
        return listaComboCliente;
    }
    public List<Cliente> buscaClienteAutoComplete(String busca) {
        List<Cliente> listaCliente = new ArrayList<>();
        try{
            listaCliente = new ClienteDAO().getClientes(busca);
            return listaCliente;
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return listaCliente;
    }
}