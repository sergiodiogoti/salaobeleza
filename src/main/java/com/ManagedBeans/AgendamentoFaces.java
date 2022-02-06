package com.ManagedBeans;

import com.Bean.*;
import com.DAO.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.Util.HibernateUtil;
import com.Util.MensagensErros;
import com.Util.UtilFaces;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypeTemplate;
import org.hibernate.type.StandardBasicTypes;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 * @author Sergio
 */
@Named
@SessionScoped
public class AgendamentoFaces implements Serializable {
    private List<Agendamento> agendamentoList = new ArrayList<>();
    private AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    private HorarioTrabalhoDAO horarioTrabalhoDAO = new HorarioTrabalhoDAO();
    private List<ItensAgendamento> itensAgendamentoList = null;
    private ItensAgendamentoDAO itensAgendamentoDAO = new ItensAgendamentoDAO();
    private ItensAgendamento itensAgendamentoSelected;
    private Agendamento agendamentoSelected;
    private Agendamento agendamentoSemana;
    private Usuario usuario;
    private List<HorarioTrabalho> horarioTrabalhoList = new ArrayList<>();
    private String diaSemana;
    private Cliente clienteSelected;
    private Profissional profissionalSelected;
    private Servico servicoSelected;
    private Map<String, String> mapAgenda = new HashMap<>();
    private Date dataSelected;
    private HorarioTrabalho horarioTrabalhoSelected;
    private String horario;
    private String parametros;
    private Date dataInicial;
    private Date dataFinal;

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getParametros() {
        if(parametros == null){
            parametros = "dia";
        }
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Agendamento getAgendamentoSemana() {
        return agendamentoSemana;
    }

    public void setAgendamentoSemana(Agendamento agendamentoSemana) {
        this.agendamentoSemana = agendamentoSemana;
    }

    public HorarioTrabalho getHorarioTrabalhoSelected() {
        return horarioTrabalhoSelected;
    }

    public void setHorarioTrabalhoSelected(HorarioTrabalho horarioTrabalhoSelected) {
        this.horarioTrabalhoSelected = horarioTrabalhoSelected;
    }

    public Date getDataSelected() {
        if (dataSelected == null) {
            dataSelected = new Date();
        }
        return dataSelected;
    }

    public HorarioTrabalhoDAO getHorarioTrabalhoDAO() {
        return horarioTrabalhoDAO;
    }

    public void setHorarioTrabalhoDAO(HorarioTrabalhoDAO horarioTrabalhoDAO) {
        this.horarioTrabalhoDAO = horarioTrabalhoDAO;
    }

    public List<ItensAgendamento> getItensAgendamentoList() {
        return itensAgendamentoList;
    }

    public void setItensAgendamentoList(List<ItensAgendamento> itensAgendamentoList) {
        this.itensAgendamentoList = itensAgendamentoList;
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

    public void setDataSelected(Date dataSelected) {
        this.dataSelected = dataSelected;
    }

    public Map<String, String> getMapAgenda() {
        return mapAgenda;
    }

    public void setMapAgenda(Map<String, String> mapAgenda) {
        this.mapAgenda = mapAgenda;
    }

    public Cliente getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Cliente clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

    public Profissional getProfissionalSelected() {
        return profissionalSelected;
    }

    public void setProfissionalSelected(Profissional profissionalSelected) {
        this.profissionalSelected = profissionalSelected;
    }

    public Servico getServicoSelected() {
        return servicoSelected;
    }

    public void setServicoSelected(Servico servicoSelected) {
        this.servicoSelected = servicoSelected;
    }

    public String getDiaSemana() {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(dataSelected);
            diaSemana = UtilFaces.getDiaSemana(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public List<HorarioTrabalho> getHorarioTrabalhoList() {
        return horarioTrabalhoList;
    }

    public void setHorarioTrabalhoList(List<HorarioTrabalho> horarioTrabalhoList) {
        this.horarioTrabalhoList = horarioTrabalhoList;
    }

    public List<Agendamento> getAgendamentoList() {
        return agendamentoList;
    }

    public void setAgendamentoList(List<Agendamento> agendamentoList) {
        this.agendamentoList = agendamentoList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public AgendamentoDAO getAgendamentoDAO() {
        return agendamentoDAO;
    }

    public void setAgendamentoDAO(AgendamentoDAO agendamentoDAO) {
        this.agendamentoDAO = agendamentoDAO;
    }

    public Agendamento getAgendamentoSelected() {
        return agendamentoSelected;
    }

    public void setAgendamentoSelected(Agendamento agendamentoSelected) {
        this.agendamentoSelected = agendamentoSelected;

    }

    public void doAddAgendamento() {
        agendamentoSelected = new Agendamento();
        clienteSelected = null;
    }

    public void setaAgendamento(){
        Session ses =HibernateUtil.getSessionFactory().openSession();
        Transaction tx  = null;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String sql = "select * from agendamento a where a.ageCliCodigo = " + clienteSelected.getCliCodigo() + "\n" +
                    "and ageData = '" + sdf.format(dataSelected) + "' LIMIT 1";
            Query q = ses.createNativeQuery(sql).addEntity(Agendamento.class);
            if(q.list().size() > 0) {
                agendamentoSelected = (Agendamento) q.uniqueResult();
            }

            if(agendamentoSelected.getAgeCodigo() == null) {
                tx = ses.beginTransaction();
                agendamentoSelected.setAgeData(dataSelected);
                UsuarioFaces usuarioFaces = UtilFaces.getManagedBean(UsuarioFaces.class);
                if ("EXTERNO".equals(usuarioFaces.getUsuarioLogadoBean().getUsuTipo())) {
                    agendamentoSelected.setAgeCliCodigo(usuarioFaces.getUsuarioLogadoBean().getUsuCliCodigo());
                }else{
                    agendamentoSelected.setAgeCliCodigo(clienteSelected);
                }
                ses.save(agendamentoSelected);
                tx.commit();
            }
        }catch(Exception e){
            e.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }finally {
            ses.close();
        }
    }

    public void dofinishAddAgendamento() {
        agendamentoDAO.addAgendamento(agendamentoSelected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Agendamento cadastrado com sucesso."));
    }

    public void doRemoveAgendamento() {
        agendamentoDAO.removeAgendamento(agendamentoSelected);
    }

    public void dofinishUpdateAgendamento() {
        agendamentoDAO.updateAgendamento(agendamentoSelected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Agendamento alterado com sucesso."));

    }

    public void doSaveorUpdateAgendamento() {
        if (agendamentoSelected.getAgeCodigo() == null) {
            dofinishAddAgendamento();
        } else {
            dofinishUpdateAgendamento();
        }
    }

    public void doSaveorUpdateItensAgendamento() {
        for(ItensAgendamento it : itensAgendamentoList){
            if(it.getItAgeHora().substring(0,5).equals(itensAgendamentoSelected.getItAgeHora().substring(0,5))){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "Já foi agendado um serviço nesse horário."));
                FacesContext.getCurrentInstance().validationFailed();
                return;
            }
        }
        for(ItensAgendamento it : itensAgendamentoList){
            if(it.getItAgeHora().substring(0,5).equals(itensAgendamentoSelected.getItAgeHora().substring(0,5))){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "Já foi agendado um serviço nesse horário."));
                FacesContext.getCurrentInstance().validationFailed();
                return;
            }
        }
        setaAgendamento();
        if (itensAgendamentoSelected.getItAgeCodigo() == null) {
            dofinishAddItensAgendamento();
        } else {
            dofinishUpdateItensAgendamento();
        }
    }

    public void verificaAgendamento() {

        if (existeAgendamentoSemana()) {
            PrimeFaces.current().executeScript("PF('alertaAgendamentoExistente').show()");
            FacesContext.getCurrentInstance().validationFailed();
            return;
        }
        doSaveorUpdateAgendamento();
    }

    public void setaHora() {
//        dataSelected = agendamentoSemana.getAgeData();
    }

    public void setaDataAgendamento() {
        agendamentoSelected.setAgeData(agendamentoSemana.getAgeData());
        doSaveorUpdateAgendamento();
    }

    public void doAddItensAgendamento() {
        itensAgendamentoSelected = new ItensAgendamento();
        itensAgendamentoSelected.setItAgeAgeCodigo(agendamentoSelected);
        itensAgendamentoSelected.setItAgeData(dataSelected);
    }

    public void dofinishAddItensAgendamento() {
        itensAgendamentoDAO.addItensAgendamento(itensAgendamentoSelected);
    }

    public void doRemoveItensAgendamento() {
        itensAgendamentoDAO.removeItensAgendamento(itensAgendamentoSelected);
    }

    public void doUpdateItensAgendamento() {

    }

    public void dofinishUpdateItensAgendamento() {
        itensAgendamentoDAO.updateItensAgendamento(itensAgendamentoSelected);
    }

    //busca Agendamentos, caso o usuario logado seja cliente (externo), tras apenas os agendamentos do cliente na data selecionada.
    //caso a busca seja por parametros tras os agendamentos baseados nos parametros selecionados.
    public void populaAgendamento() {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        agendamentoList = new ArrayList<>();
        try {
            UsuarioFaces usuarioFaces = UtilFaces.getManagedBean(UsuarioFaces.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String clausulaCliente = "true";
            String clausulaData = "";
            if ("EXTERNO".equals(usuarioFaces.getUsuarioLogadoBean().getUsuTipo())) {
                if (null == usuarioFaces.getUsuarioLogadoBean().getUsuCliCodigo()) {
                    return;
                }
                clausulaCliente = "ageCliCodigo =" + usuarioFaces.getUsuarioLogadoBean().getUsuCliCodigo().getCliCodigo();
            }
            if("dia".equals(parametros)){
                clausulaData = "ageData = '" + sdf.format(dataSelected) + "'";
            }
            if("periodo".equals(parametros)){
                if(null == dataInicial || null == dataFinal){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "Data inicial e final precisam ser preenchidas"));
                    FacesContext.getCurrentInstance().validationFailed();
                    return;
                }
                clausulaData = "ageData BETWEEN '" + sdf.format(dataInicial) + "' AND  '" + sdf.format(dataFinal) + "'";
            }
            String sql = "SELECT * FROM agendamento " +
                    "WHERE " + clausulaCliente + "\n" +
                    "AND " + clausulaData;
            Query q = ses.createNativeQuery(sql).addEntity(Agendamento.class);
            agendamentoList = q.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ses.close();
        }
    }


    //busca os agendamento do cliente e data selecionados.
    public void populaItensAgendamento() {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        itensAgendamentoList = new ArrayList<>();
        try {
            if(agendamentoSelected.getAgeCodigo() == null){
                return;
            }
            String sql = "SELECT * FROM itensagendamento it WHERE itAgeAgeCodigo = " + agendamentoSelected.getAgeCodigo();
            Query q = ses.createNativeQuery(sql).addEntity(ItensAgendamento.class);
            itensAgendamentoList = q.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ses.close();
        }
    }

    //seta a Data selecionada ao clicar no calendario da tela Principal do Agendamento.
    public void setaData(SelectEvent event) {
        dataSelected = (Date) event.getObject();
    }

    //seta o Cliente selecionado no autocomplete
    public void handlerCliente(SelectEvent event) {
        clienteSelected = (Cliente) event.getObject();
        setaAgendamento();
    }

    //seta o Profissional selecionado no autocomplete
    public void handlerProfissional(SelectEvent event) {
      itensAgendamentoSelected.setItAgeProCodigo((Profissional) event.getObject());
    }

    //seta o Servico selecionado no autocomplete
    public void handlerServico(SelectEvent event) {
        itensAgendamentoSelected.setItAgeSerCodigo((Servico) event.getObject());
    }

    //busca horarios disponiveis que não têm agendamento na data e horario com profissional selecionado.
    public void populaHorarioDisponiveisAgendamento() {
        horarioTrabalhoList = new ArrayList<>();
        try {
            horarioTrabalhoList = agendamentoDAO.listaHorarioDisponivelParaAgendamento(itensAgendamentoSelected.getItAgeProCodigo(),dataSelected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //busca horarios disponiveis do profissional logado na data selecionada.
    public void populaHorarioDisponiveisProfissional() {
        horarioTrabalhoList = new ArrayList<>();
        try {
            UsuarioFaces usuarioFaces = UtilFaces.getManagedBean(UsuarioFaces.class);
            horarioTrabalhoList = agendamentoDAO.listaHorarioDisponivelProfissional(usuarioFaces.getUsuarioLogadoBean().getUsuProCodigo(),dataSelected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //verifica se já existe um agendamento com situação aguardando na semana da data selecionada,
    // caso o salao tenha mais de um profissional, busca o agendamento por profissional,
    // levando em conta que a semana começa numa segunda e termina no domingo
    public boolean existeAgendamentoSemana() {
        try {
            agendamentoSemana = null;
            agendamentoSemana = agendamentoDAO.agendamentoExistente(agendamentoSelected);
            if (null != agendamentoSemana)
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void doUpdateAgendamento() {
       // horario = agendamentoSelected.getAgeHoraInicio().substring(0, 5);
    }

    public String getPermiteAlteracao(Date ageDate) {
        int intervaloDias = 0;
        try(Session ses = HibernateUtil.getSessionFactory().openSession()){
            String sql = "SELECT DATEDIFF ('"+new SimpleDateFormat("yyyy-MM-dd").format(ageDate)+"',NOW()) FROM DUAL;";
            Query q = ses.createNativeQuery(sql);
            intervaloDias = q.list().size();
            UsuarioFaces usuarioFaces = UtilFaces.getManagedBean(UsuarioFaces.class);
            if(intervaloDias <= 2 && "EXTERNO".equals(usuarioFaces.getUsuarioLogadoBean().getUsuTipo())){
               return "N";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "S";
    }
}