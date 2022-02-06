package com.ManagedBeans;

import com.DAO.ItensAgendamentoDAO;
import com.Bean.ItensAgendamento;
import com.Bean.Usuario;
import com.DAO.UsuarioDAO;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import com.Util.MensagensErros;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author Sergio
 */
@Named
@SessionScoped
public class ItensAgendamentoFaces implements Serializable{

    private List<ItensAgendamento> cachedItensAgendamentos = null;
    private ItensAgendamentoDAO itensAgendamentoDAO = new ItensAgendamentoDAO();
    private ItensAgendamento itensAgendamentoSelected;
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ItensAgendamentoDAO getItensAgendamentoDAO() {
        return itensAgendamentoDAO;
    }

    public void setItensAgendamentoDAO(ItensAgendamentoDAO itensAgendamentoDAO) {
        this.itensAgendamentoDAO = itensAgendamentoDAO;
    }

    public ItensAgendamento getItensAgendamentoSelected() {
        return itensAgendamentoSelected;
    }

    public void setItensAgendamentoSelected(ItensAgendamento itensAgendamentoSelected) {
        this.itensAgendamentoSelected = itensAgendamentoSelected;
    }

    public List<ItensAgendamento> getCachedItensAgendamentos() {
        if (cachedItensAgendamentos == null) {
            this.cachedItensAgendamentos = itensAgendamentoDAO.getItensAgendamentos();
        }
        return cachedItensAgendamentos;
    }

    public String doAddItensAgendamento() {
        itensAgendamentoSelected = new ItensAgendamento();
        return "itensAgendamentoNew";
    }

    public String dofinishAddItensAgendamento() {
        Date date = new Date();
        itensAgendamentoDAO.addItensAgendamento(itensAgendamentoSelected);
        cachedItensAgendamentos = null;
        if (MensagensErros.isErro()) {
            return "";
        } else {
            return "itensAgendamentoList";
        }
    }

    public String doRemoveItensAgendamento() {
        itensAgendamentoDAO.removeItensAgendamento(itensAgendamentoSelected);
        cachedItensAgendamentos = null;
        if (MensagensErros.isErro()) {
            return "";
        } else {
            return "itensAgendamentoList";
        }
    }

    public String doUpdateItensAgendamento() {
        return "itensAgendamentoEdit";
    }

    public String dofinishUpdateItensAgendamento() {
        itensAgendamentoDAO.updateItensAgendamento(itensAgendamentoSelected);
        cachedItensAgendamentos = null;
        if (MensagensErros.isErro()) {
            return "";
        } else {
            return "itensAgendamentoList";
        }
    }

    public String doCancelItensAgendamento() {
        cachedItensAgendamentos = null;
        return "itensAgendamentoList";
    }
}