package com.ManagedBeans;

import com.Bean.Cliente;
import com.DAO.ClienteDAO;
import com.DAO.ProfissionalDAO;
import com.Bean.Profissional;
import com.Bean.Usuario;
import com.DAO.UsuarioDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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
public class ProfissionalFaces implements Serializable{

    private List<Profissional> profissionalList = new ArrayList<>();
    private ProfissionalDAO profissionalDAO = new ProfissionalDAO();
    private Profissional profissionalSelected;
    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public ProfissionalDAO getProfissionalDAO() {
        return profissionalDAO;
    }

    public void setProfissionalDAO(ProfissionalDAO profissionalDAO) {
        this.profissionalDAO = profissionalDAO;
    }

    public Profissional getProfissionalSelected() {
        return profissionalSelected;
    }

    public void setProfissionalSelected(Profissional profissionalSelected) {
        this.profissionalSelected = profissionalSelected;
    }

    public List<Profissional> getProfissionalList() {
        return profissionalList;
    }

    public void setProfissionalList(List<Profissional> profissionalList) {
        this.profissionalList = profissionalList;
    }

    public void doAddProfissional() {
        profissionalSelected = new Profissional();
    }

    public void doSaveOrUpdateProfissional(){
        if(profissionalSelected.getProCodigo() == null){
            if(!profissionalDAO.existeProfissional(profissionalSelected.getProCPF())) {
                profissionalDAO.addProfissional(profissionalSelected);
            }
            else{
                UtilFaces.addMensagem("Já existe um profissional cadastrado com o CPF informado", FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().validationFailed();
            }
        }else{
            profissionalSelected.setDataHoraAtualizacao(new Date());
            profissionalDAO.updateProfissional(profissionalSelected);
        }
    }

    public void doRemoveProfissional() {
        profissionalDAO.removeProfissional(profissionalSelected);
    }

    public void populaProfissional(){
        profissionalList = new ArrayList<>();
        try{
            profissionalList = profissionalDAO.getProfissionais(param);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<Profissional> buscaProfissionalAutoComplete(String busca) {
        List<Profissional> listaProfissional = new ArrayList<>();
        try{
            listaProfissional = new ProfissionalDAO().getProfissionais(busca);
            return listaProfissional;
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return listaProfissional;
    }
    public List<SelectItem> getComboProfissionals() {
        List<SelectItem> listaComboProfissional = new ArrayList<SelectItem>(0);
        List<Profissional> listaProfissional = new ProfissionalDAO().getProfissionais(null);
        for (Profissional p : listaProfissional) {
            SelectItem item = new SelectItem();
            item.setLabel(p.getProNome());
            item.setValue(p);
            listaComboProfissional.add(item);
        }
        return listaComboProfissional;
    }
}