package com.ManagedBeans;

import com.Bean.Profissional;
import com.DAO.HorarioTrabalhoDAO;
import com.Bean.HorarioTrabalho;
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
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Sergio
 */
@Named
@SessionScoped
public class HorarioTrabalhoFaces implements Serializable{

    private List<HorarioTrabalho> horarioTrabalhoList = new ArrayList<>();
    private HorarioTrabalhoDAO horarioTrabalhoDAO = new HorarioTrabalhoDAO();
    private HorarioTrabalho horarioTrabalhoSelected;
    private Usuario usuario;
    private Profissional profissionalSelected;

    public Profissional getProfissionalSelected() {
        return profissionalSelected;
    }

    public void setProfissionalSelected(Profissional profissionalSelected) {
        this.profissionalSelected = profissionalSelected;
    }

    public List<HorarioTrabalho> getHorarioTrabalhoList() {
        return horarioTrabalhoList;
    }

    public void setHorarioTrabalhoList(List<HorarioTrabalho> horarioTrabalhoList) {
        this.horarioTrabalhoList = horarioTrabalhoList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public HorarioTrabalhoDAO getHorarioTrabalhoDAO() {
        return horarioTrabalhoDAO;
    }

    public void setHorarioTrabalhoDAO(HorarioTrabalhoDAO horarioTrabalhoDAO) {
        this.horarioTrabalhoDAO = horarioTrabalhoDAO;
    }

    public HorarioTrabalho getHorarioTrabalhoSelected() {
        return horarioTrabalhoSelected;
    }

    public void setHorarioTrabalhoSelected(HorarioTrabalho horarioTrabalhoSelected) {
        this.horarioTrabalhoSelected = horarioTrabalhoSelected;
    }

    public void doAddHorarioTrabalho() {
        horarioTrabalhoSelected = new HorarioTrabalho();
        horarioTrabalhoSelected.setHorTraProCodigo(profissionalSelected);
    }

    public void dofinishAddHorarioTrabalho() {
        horarioTrabalhoDAO.addHorarioTrabalho(horarioTrabalhoSelected);
    }

    public void doRemoveHorarioTrabalho() {
        horarioTrabalhoDAO.removeHorarioTrabalho(horarioTrabalhoSelected);
    }

    public void dofinishUpdateHorarioTrabalho() {
        horarioTrabalhoDAO.updateHorarioTrabalho(horarioTrabalhoSelected);
    }

    public void onAddNew() {
        try{
            HorarioTrabalho h = new HorarioTrabalho();
            h.setHorTraProCodigo(profissionalSelected);
            horarioTrabalhoDAO.addHorarioTrabalho(h);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onRemove() {
        try{
            int conta = 0;
            HorarioTrabalho horario = null;
            for(HorarioTrabalho h : horarioTrabalhoList){
                conta++;
                if(conta == horarioTrabalhoList.size()){
                    horario = h;
                }

            }
            if(null != horario)
            horarioTrabalhoDAO.removeHorarioTrabalho(horario);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void populaHorarioTrabalho(){
        horarioTrabalhoList = new ArrayList<>();
        try{
            horarioTrabalhoList = horarioTrabalhoDAO.getHorarioTrabalhos(profissionalSelected);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onCellEdit(CellEditEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();
        HorarioTrabalho h = fc.getApplication().evaluateExpressionGet(fc,"#{hor}",HorarioTrabalho.class);
        horarioTrabalhoDAO.updateHorarioTrabalho(h);
    }

}