package com.ManagedBeans;

import com.Bean.Cliente;
import com.Bean.Profissional;
import com.Conexao.ConexaoMySQL;
import com.DAO.UsuarioDAO;
import com.Bean.Usuario;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.Util.HibernateUtil;
import com.Util.MensagensErros;
import com.Util.UtilFaces;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.primefaces.barcelona.view.GuestPreferences;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sergio
 */
@Named
@SessionScoped
public class UsuarioFaces implements Serializable{

    private List<Usuario> usuarioList = null;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Usuario usuarioSelected;
    private Usuario usuario;
    private boolean existUser;
    private String email,password;
    private Usuario usuarioLogadoBean;
    private  String corTema;
    private String param;
    private String tipoUsuario;

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getCorTema() {
        if(null == corTema){
            corTema = "blue";
        }
        return corTema;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public void setCorTema(String corTema) {
        this.corTema = corTema;
    }

    public Usuario getUsuarioLogadoBean() {
        return usuarioLogadoBean;
    }

    public void setUsuarioLogadoBean(Usuario usuarioLogadoBean) {
        this.usuarioLogadoBean = usuarioLogadoBean;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isExistUser() {
        return existUser;
    }

    public void setExistUser(boolean existUser) {
        this.existUser = existUser;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public String doAddUsuario() {
        usuarioSelected = new Usuario();
        return "conta?faces-redirect=true";
    }

    public String dofinishAddUsuario() {
        if(usuarioDAO.existeUsuario(usuarioSelected.getUsuEmail(),"email")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção!", "E-mail já existe."));
            FacesContext.getCurrentInstance().validationFailed();
            return "";
        }
        if(usuarioDAO.existeUsuario(usuarioSelected.getUsuLogin(),"login")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Atenção!", "Login já existe."));
            FacesContext.getCurrentInstance().validationFailed();
            return "";
        }
        Date date = new Date();
        usuarioSelected.setDataHoraAtualizacao(date);
        usuarioSelected.setUsuTipo(tipoUsuario);
        usuarioDAO.addUsuario(usuarioSelected);
        if (MensagensErros.isErro()) {
            return "";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info!", "Usuario cadastrado com sucesso."));
            FacesContext.getCurrentInstance().validationFailed();
            return "login?faces-redirect=true";
        }
    }

    public String doRemoveUsuario() {
        usuarioDAO.removeUsuario(usuarioSelected);
        if (MensagensErros.isErro()) {
            return "";
        } else {
            return "";
        }
    }

    public void doSaveOrUpdateUsuario(){
        if(usuarioSelected.getUsuCodigo() == null){
            if(!usuarioDAO.existeUsuario(usuarioSelected.getUsuEmail(),"email")) {
                usuarioDAO.addUsuario(usuarioSelected);
            }
            else{
                UtilFaces.addMensagem("Já existe um usuario cadastrado com o CPF informado", FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().validationFailed();
            }
        }else{
            usuarioSelected.setDataHoraAtualizacao(new Date());
            usuarioDAO.updateUsuario(usuarioSelected);
        }
    }

    public void populaUsuario(){
        usuarioList = new ArrayList<>();
        try{
            usuarioList = usuarioDAO.getUsuarios(param);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void usuarioExists() {
        existUser= false;
        try {
            Usuario usu = usuarioDAO.usuarioValidado(email,password);
            if(null != usu){
                existUser= true;
                //Apos validar o usuario coloca o usuario na sessão.
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario",usu);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String doLogin() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario",null);
        if(null == email || "".equals(email)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "Campo Usuário não foi preenchido."));
            return  null;
        }
        if(null == password || "".equals(password)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "Campo Senha não foi preenchido."));
            return  null;
        }
        usuarioExists();
        if(!existUser){
            setPassword(null);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Email/Senha Incorretos, por favor verifique os seus dados."));
            return  null;
        }else{
            usuarioLogadoBean = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            GuestPreferences guestPreferences = UtilFaces.getManagedBean(GuestPreferences.class);
            String cor = "cyan";
            if(usuarioLogadoBean.getUsuTema() != null){
                cor = usuarioLogadoBean.getUsuTema();
            }
            guestPreferences.setTheme(cor);
            corTema = usuarioLogadoBean.getUsuTema();
            return "dashboard?faces-redirect=true";
        }
    }
    public void doLogout() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        setEmail(null);
        setPassword(null);
        //retira o usuario da sessão.
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario",null);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        FacesContext.getCurrentInstance().getExternalContext().redirect(ec.getRequestContextPath()+"/login.xhtml");
    }

    public String transformTextInicialMaiusculo(String str){
        String textTransformado = "";
        try {
            if(null == str || "".equals(str)){
                return "";
            }
            String transf = str.substring(0,1).toUpperCase();
            textTransformado = transf + "" + str.substring(1, str.length()).toLowerCase();
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return textTransformado;
    }

    public void doUpdateSenha(){
        if(!usuarioSelected.getUsuSenhaAntiga().equals(usuarioSelected.getUsuSenha())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "Senha antiga não corresponde"));
            FacesContext.getCurrentInstance().validationFailed();
            return;
        }
        if(usuarioSelected.getUsuSenhaAntiga().equals(usuarioSelected.getUsuNovaSenha())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "A senha nova não pode ser igual a senha antiga"));
            FacesContext.getCurrentInstance().validationFailed();
            return;
        }
        if(!usuarioSelected.getUsuNovaSenha().equals(usuarioSelected.getUsuConfirmaSenha())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "A senha nova não é igual a senha de confirmação"));
            FacesContext.getCurrentInstance().validationFailed();
            return;
        }
        usuarioSelected.setUsuSenha(usuarioSelected.getUsuNovaSenha());
        usuarioDAO.updateUsuario(usuarioSelected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK!", "Senha alterada com sucesso"));
        return;
    }
    public void changeTemaUsuario(String cor){
        Date date = new Date();
        if(null == cor){
            cor = "blue";
        }
        if(usuarioLogadoBean != null) {
            usuarioLogadoBean.setUsuTema(cor);
            usuarioLogadoBean.setDataHoraAtualizacao(date);
            usuarioDAO.updateUsuario(usuarioLogadoBean);
        }

    }
    //seta o Cliente selecionado no autocomplete
    public void handlerCliente(SelectEvent event) {
        usuarioSelected.setUsuCliCodigo((Cliente) event.getObject());
        usuarioSelected.setUsuTipo("EXTERNO");
        usuarioSelected.setUsuProCodigo(null);
    }
    //seta o Profissional selecionado no autocomplete
    public void handlerProfissional(SelectEvent event) {
        usuarioSelected.setUsuProCodigo((Profissional) event.getObject());
        usuarioSelected.setUsuTipo("INTERNO");
        usuarioSelected.setUsuCliCodigo(null);
    }

}