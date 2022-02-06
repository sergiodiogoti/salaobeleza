package com.ManagedBeans;

import com.Bean.Profissional;
import com.DAO.ProfissionalDAO;
import com.DAO.ServicoDAO;
import com.Bean.Servico;
import com.Bean.Usuario;
import com.DAO.UsuarioDAO;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import com.Util.MensagensErros;
import com.Util.UtilFaces;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Sergio
 */
@Named
@SessionScoped
public class ServicoFaces implements Serializable{

    private List<Servico> servicoList = new ArrayList<>();
    private ServicoDAO servicoDAO = new ServicoDAO();
    private Servico servicoSelected;
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

    public ServicoDAO getServicoDAO() {
        return servicoDAO;
    }

    public void setServicoDAO(ServicoDAO servicoDAO) {
        this.servicoDAO = servicoDAO;
    }

    public Servico getServicoSelected() {
        return servicoSelected;
    }

    public void setServicoSelected(Servico servicoSelected) {
        this.servicoSelected = servicoSelected;
    }

    public List<Servico> getServicoList() {
        return servicoList;
    }

    public void setServicoList(List<Servico> servicoList) {
        this.servicoList = servicoList;
    }

    public void doAddServico() {
        servicoSelected = new Servico();
    }

    public void dofinishAddServico() {
        Date date = new Date();
        servicoDAO.addServico(servicoSelected);
    }

    public void doRemoveServico() {
        servicoDAO.removeServico(servicoSelected);
    }

    public void dofinishUpdateServico() {
        Date date = new Date();
        servicoDAO.updateServico(servicoSelected);
    }

    public void doSaveOrUpdateServico(){
        if(servicoSelected.getSerCodigo() == null){
            if(!servicoDAO.existeProfissional(servicoSelected.getSerDescricao())) {
                servicoDAO.addServico(servicoSelected);
            }
            else{
                UtilFaces.addMensagem("Já existe um serviço cadastrado com a descrição informada", FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().validationFailed();
            }
        }else{
            servicoDAO.updateServico(servicoSelected);
        }
    }

    public void populaServico(){
        servicoList = new ArrayList<>();
        try{
            servicoList = servicoDAO.getServicos(param);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String moedaBr(BigDecimal vlr) {
            Locale.setDefault(new Locale("pt", "BR"));
            DecimalFormat df = new DecimalFormat();
            df.applyPattern("R$ #,##0.00");
            double valorConvertido = vlr.doubleValue();
            return df.format(valorConvertido);
    }

    public List<SelectItem> getComboServicos(){
        List<SelectItem> listaComboServico = new ArrayList<SelectItem>(0);
        List<Servico> listaServico = new ServicoDAO().getServicos(null);
        for (Servico s : listaServico) {
            SelectItem item = new SelectItem();
            item.setLabel(s.getSerDescricao());
            item.setValue(s);
            listaComboServico.add(item);
        }
        return listaComboServico;
    }
    public List<Servico> buscaServicoAutoComplete(String busca) {
        List<Servico> listaServico = new ArrayList<>();
        try{
            listaServico = new ServicoDAO().getServicos(busca);
            return listaServico;
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return listaServico;
    }

}