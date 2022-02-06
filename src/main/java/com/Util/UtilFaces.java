/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Util;

import com.Bean.*;
import com.ManagedBeans.UsuarioFaces;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.lang.InstantiationException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Pattern;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.model.SelectItem;

import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.Type;




/**
 * Essa classe possui métodos de uso geral como salvar um valor na sessão,
 * remover o valor, criar FacesMessages, etc.
 *
 * @author Sergio
 */
public class UtilFaces implements Serializable{


    private Usuario usuarioLogadoBean;
    private List<String> bloqueioLogadoBean;
    private String contexto;
    private UsuarioFaces usuarioFaces = null;


    public Usuario getUsuarioLogadoBean() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        usuarioFaces = (UsuarioFaces) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "usuarioFaces");
        usuarioLogadoBean = usuarioFaces.getUsuarioLogadoBean();
        return usuarioLogadoBean;
    }

    public void setUsuarioLogadoBean(Usuario usuarioLogadoBean) {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        usuarioFaces = (UsuarioFaces) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "usuarioFaces");
        usuarioFaces.setUsuarioLogadoBean(usuarioLogadoBean);
        this.usuarioLogadoBean = usuarioLogadoBean;
    }

    /**
     * Verivica se um determinado valor existe na sessão
     *
     * @param valor
     * @return true ou false
     */
    public static boolean existeNaSessao(String valor) {

        Object verifica = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(valor);

        if (verifica != null) { //valor existe na sessão
            return true;
        }

        return false;
    }

    /**
     * Adiciona um dado(valor) na sessão
     *
     * @param Key   A chave
     * @param valor O valor
     */
    public static void addSessao(String Key, Object valor) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Key, valor);
    }

    /**
     * Remove um valor da sessão
     *
     * @param key
     */
    public static void removeSessao(String key) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);
    }

    /**
     * Retorna um objeto que está na sessão
     *
     * @param Key
     * @return
     */
    public static Object getSessao(String Key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Key);
    }

    /**
     * Adiciona uma mensagem no ciclo atual do faces
     *
     * @param msg
     * @param severity
     */
    public static void addMensagem(String msg, FacesMessage.Severity severity) {
        FacesMessage mensagem = new FacesMessage();
        mensagem.setSeverity(severity);
        mensagem.setSummary("Atenção");
        mensagem.setDetail(msg);
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
    }

    public static void addValidationExceptionError(String titu, String descricao) {
        FacesMessage f = new FacesMessage(FacesMessage.SEVERITY_ERROR, titu, descricao);
        FacesContext.getCurrentInstance().addMessage(null, f);
        FacesContext.getCurrentInstance().validationFailed();
        MensagensErros.setErro(true);
        throw new AbortProcessingException();
    }

    public static byte[] fileToByte(File imagem) throws Exception {
        FileInputStream fis = new FileInputStream(imagem);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead = 0;
        while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }

    /**
     * Retorna um parâmetro da url
     *
     * @param chave -O parâmetro
     * @return
     */
    public static String getParametro(String chave) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(chave);
    }

    /**
     * Lista de Ufs
     */
    public static SelectItem[] siglasEstado = {
            new SelectItem("SP", "SP"),
            new SelectItem("AC", "AC"),
            new SelectItem("AL", "AL"),
            new SelectItem("AM", "AM"),
            new SelectItem("AP", "AP"),
            new SelectItem("BA", "BA"),
            new SelectItem("CE", "CE"),
            new SelectItem("DF", "DF"),
            new SelectItem("ES", "ES"),
            new SelectItem("GO", "GO"),
            new SelectItem("MA", "MA"),
            new SelectItem("MG", "MG"),
            new SelectItem("MS", "MS"),
            new SelectItem("MT", "MT"),
            new SelectItem("PA", "PA"),
            new SelectItem("PB", "PB"),
            new SelectItem("PE", "PE"),
            new SelectItem("PI", "PI"),
            new SelectItem("PR", "PR"),
            new SelectItem("RJ", "RJ"),
            new SelectItem("RN", "RN"),
            new SelectItem("RO", "RO"),
            new SelectItem("RR", "RR"),
            new SelectItem("RS", "RS"),
            new SelectItem("SC", "SC"),
            new SelectItem("SE", "SE"),
            new SelectItem("SP", "SP"),
            new SelectItem("TO", "TO")
    };
    /**
     * Select item de sexo
     */
    public static SelectItem[] sexo = {
            new SelectItem("M", "Masculino"),
            new SelectItem("F", "Feminino")
    };
    /**
     * Tipode de logradouro
     */
    public static SelectItem[] tipoLogradouro = {
            new SelectItem("RUA", "Rua"),
            new SelectItem("AVENIDA", "Avenida"),
            new SelectItem("TRAVESSA", "Travessa"),
            new SelectItem("ALAMEDA", "Alemeda"),
            new SelectItem("BECO", "Beco")
    };
    /**
     * Estado civil
     */
    public static SelectItem[] estadoCivil = {
            new SelectItem("SOLTEIRO(A)", "SOLTEIRO(A)"),
            new SelectItem("CASADO(A)", "CASADO(A)"),
            new SelectItem("DIVORCIADO(A)", "DIVORCIADO(A)"),
            new SelectItem("SEPARADO(A)", "SEPARADO(A)"),
            new SelectItem("VIÚVO(A)", "VIÚVO(A)")
    };
    /**
     * EScolha Sim/Não
     */
    public static SelectItem[] simNao = {
            new SelectItem("SIM", "SIM"),
            new SelectItem("NÃO", "NÃO")
    };
    public static SelectItem[] mes = {
            new SelectItem("JANEIRO", "JANEIRO"),
            new SelectItem("FEVEREIRO", "FEVEREIRO"),
            new SelectItem("MARCO", "MARCO"),
            new SelectItem("ABRIL", "ABRIL"),
            new SelectItem("MAIO", "MAIO"),
            new SelectItem("JUNHO", "JUNHO"),
            new SelectItem("JULHO", "JULHO"),
            new SelectItem("AGOSTO", "AGOSTO"),
            new SelectItem("SETEMBRO", "SETEMBRO"),
            new SelectItem("OUTUBRO", "OUTUBRO"),
            new SelectItem("NOVEMBRO", "NOVEMBRO"),
            new SelectItem("DEZEMBRO", "DEZEMBRO")};

    /**
     * Verifica se uma string contém somente números inteiros
     *
     * @param string
     * @return true ou false
     */
    public static boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getHoraCorrente() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", new Locale("pt", "BR"));
        Date data = new Date();
        String horaAtual = dateFormat.format(data);
        return horaAtual;
    }

    public static <T extends Serializable> List<T> getBuscaGeneric(Class<T> classtoCast, String query, Object... params) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        try {
            ses.beginTransaction();
            //fazer consulta sql não hql
            Query qr = ses.createSQLQuery(query).addEntity(classtoCast);
            for (int i = 1; i <= params.length; i++) {
                qr.setParameter(i - 1, params[i - 1]);
            }
            @SuppressWarnings("unchecked")
            List<T> toReturn = qr.list();

            ses.getTransaction().commit();
            return toReturn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            ses.close();
        }
    }

    public static List<Object[]> getQuerySQLGeneric(String query, String[] campos, Type... params) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> toReturn = new ArrayList<Object[]>();
        try {
            ses.beginTransaction();
            //fazer consulta sql não hql
            SQLQuery qr = ses.createSQLQuery(query);
            for (int i = 1; i <= params.length; i++) {
                qr.addScalar(campos[i - 1], params[i - 1]);
            }
            toReturn = (List<Object[]>) qr.list();

            ses.getTransaction().commit();
            return toReturn;
        } catch (Exception e) {
            e.printStackTrace();
            return toReturn;
        } finally {
            ses.close();
        }
    }

    public static <T extends Serializable> Object getObjectQuerySQL(Class<T> classtoCast, String query) {
        Session ses = HibernateUtil.getSessionFactory().openSession();
        try {
            ses.beginTransaction();
            //fazer consulta sql não hql
            Query qr = ses.createNativeQuery(query).addEntity(classtoCast);
            ses.getTransaction().commit();
            return qr.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            ses.close();
        }
    }

    public static <T extends Serializable> Object getObjectoQuerySQL(Session ses, Class<T> classtoCast, String query) {
        try {
            SQLQuery qr = ses.createSQLQuery(query).addEntity(classtoCast);
            return qr.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static synchronized String getFacesRedirect() {
        return "?faces-redirect=true";
    }

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static String somenteNumeros(String str) {
        if (str != null) {
            return str.replaceAll("[^0123456789]", "");
        } else {
            return "";
        }
    }

    public static synchronized boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else {
            if (obj instanceof String) {
                return obj.toString().trim().isEmpty() || "null".equals(obj.toString());
            }

            if (obj instanceof StringBuilder) {
                return ((StringBuilder) obj).length() <= 0;
            }

            if (obj instanceof List) {
                return ((List) obj).isEmpty();
            }

            if (obj instanceof ArrayList) {
                return ((ArrayList) obj).isEmpty();
            }

            if (obj instanceof String[]) {
                return ((String[]) obj).length > 0;
            }
            return false;
        }
    }

    /**
     * Recupera o ManagedBean, caso não esteja instanciado na sessão cria um
     * novo e seta na sessão.
     *
     * @param <T>
     * @param type
     * @return
     */
    public static <T> T getManagedBean(Class<T> type) {
        try {
            String[] split = type.toString().split(Pattern.quote("."));
            String key = split[split.length - 1];
            key = key.substring(0, 1).toLowerCase() + key.substring(1, key.length());
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            Object managedBean = FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, key);
            if (managedBean == null) {
                managedBean = type.newInstance();
                FacesContext.getCurrentInstance().getApplication().getELResolver().setValue(elContext, null, key, managedBean);
            }
            return (T) managedBean;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formataData(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);

        Integer dia = cal.get(Calendar.DAY_OF_MONTH);
        Integer mes = (cal.get(Calendar.MONTH) + 1);
        Integer ano = cal.get(Calendar.YEAR);

        String dtaFormatada;

        if (dia < 10) {
            dtaFormatada = "0" + dia + "/";
        } else {
            dtaFormatada = dia + "/";
        }

        if (mes < 10) {
            dtaFormatada = dtaFormatada + "0" + mes + "/";
        } else {
            dtaFormatada = dtaFormatada + mes + "/";
        }

        dtaFormatada = dtaFormatada + ano;

        return dtaFormatada;
    }

    public static Integer getIdade(Date data, Date dataBase) {
        Calendar dataNascimento = Calendar.getInstance();
        dataNascimento.setTime(data);
        Calendar dataAtual = Calendar.getInstance();
        dataAtual.setTime(dataBase);

        Integer diferencaMes = dataAtual.get(Calendar.MONTH) - dataNascimento.get(Calendar.MONTH);
        Integer diferencaDia = dataAtual.get(Calendar.DAY_OF_MONTH) - dataNascimento.get(Calendar.DAY_OF_MONTH);
        Integer idade = (dataAtual.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR));

        if (diferencaMes < 0 || (diferencaMes == 0 && diferencaDia < 0)) {
            idade--;
        }
        return idade;
    }



    private static boolean isAnoBissexto(int ano) {
        return ((ano % 4 == 0) && ((ano % 100 != 0) || (ano % 400 == 0)));
    }

    private static int getDiasNoMes(int ano, int mes) {
        int[] mes_dias = new int[13];

        int resultado = 0;

        mes_dias[0] = 0;
        mes_dias[1] = 31;
        mes_dias[2] = 28;
        mes_dias[3] = 31;
        mes_dias[4] = 30;
        mes_dias[5] = 31;
        mes_dias[6] = 30;
        mes_dias[7] = 31;
        mes_dias[8] = 31;
        mes_dias[9] = 30;
        mes_dias[10] = 31;
        mes_dias[11] = 30;
        mes_dias[12] = 31;

        if ((mes == 2) && (isAnoBissexto(ano))) {
            resultado = 29;
        } else {
            resultado = mes_dias[mes];
        }

        return resultado;
    }

    public static String getIdadePorExtenso(Date dataNasc, Date dataBase) {
        Calendar cal = Calendar.getInstance();
        String idade = "";

        cal.setTime(dataNasc);

        int diaNasc = cal.get(Calendar.DAY_OF_MONTH);
        int mesNasc = (cal.get(Calendar.MONTH) + 1);
        int anoNasc = cal.get(Calendar.YEAR);

        cal.setTime(dataBase);

        int diaAtual = cal.get(Calendar.DAY_OF_MONTH);
        int mesAtual = (cal.get(Calendar.MONTH) + 1);
        int anoAtual = cal.get(Calendar.YEAR);

        if (diaAtual < diaNasc) {
            mesAtual--;
            diaAtual += getDiasNoMes(anoNasc, mesNasc);
        }
        if (mesAtual < mesNasc) {
            anoAtual--;
            mesAtual += 12;
        }
        int Ano = anoAtual - anoNasc;
        int Mes = mesAtual - mesNasc;
        int Dia = diaAtual - diaNasc;

        String AnoSingPlural = "";
        String MesSingPlural = "";
        String DiaSingPlural = "";
        String ano = String.valueOf(Ano);
        String mes = String.valueOf(Mes);
        String dia = String.valueOf(Dia);
        if (Ano > 1) {
            AnoSingPlural = " Anos ";
        } else {
            AnoSingPlural = " Ano ";
        }
        if (Mes > 1) {
            MesSingPlural = " Meses ";
        } else {
            MesSingPlural = " Mês ";
        }
        if (Dia > 1) {
            DiaSingPlural = " Dias";
        } else {
            DiaSingPlural = " Dia";
        }
        if (Ano == 0) {
            ano = "";
            AnoSingPlural = "";
        }
        if (Mes == 0) {
            mes = "";
            MesSingPlural = "";
        }
        if (Dia == 0) {
            dia = "";
            DiaSingPlural = "";
        }
        idade = ano + AnoSingPlural + mes + MesSingPlural + dia + DiaSingPlural;
        return idade;
    }

    public static String formatDate(Date date) {
        return year(date) + "-" + month(date) + "-" + day(date);
    }

    public static int day(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int month(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int year(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static synchronized String convertDataBrToEua(String dataBr) {
        try {
            String[] array = dataBr.split("/");
            return array[2] + "-" + array[1] + "-" + array[0];
        } catch (Exception e) {
        }
        return null;
    }

    public static synchronized String mascaraCPF(String cpf) {
        if (isEmpty(cpf)) {
            return "";
        }
        if (cpf.length() != 11) {
            return cpf;
        }
        cpf = removerAcentos(cpf);

        try {
            StringBuilder stringBuilder = new StringBuilder(cpf);
            stringBuilder.insert(3, '.').insert(7, '.').insert(11, '-');
            return stringBuilder.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static Object ifNullOrEmpty(Object str, Object retorno) {
        if (UtilFaces.isEmpty(str)) {
            return retorno;
        }

        return str;
    }

    public static synchronized int getAnoAtual() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static boolean isOnlyNumber(String value) {
        if (isNotBlank(value)) {
            return value.matches("[0-9]+");
        }
        return false;
    }

    public static boolean isNotBlank(String nome) {
        return !isBlank(nome);
    }

    public static boolean isBlank(String text) {
        if ((text != null) && (text.length() > 0)) {
            int i = 0;
            for (int iSize = text.length(); i < iSize; i++) {
                if (text.charAt(i) != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static synchronized NativeQuery createNativeQuery(Session ses, String sql) {
        return ses.createNativeQuery(sql);
    }


    public static Calendar getCalendarPtBr() {
        TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
        Calendar cal = Calendar.getInstance(tz, new Locale("pt", "br"));
        return cal;
    }

    public static synchronized boolean comparaDataHoje(Date data){
        if(data == null){
            return false;
        }
        StringBuilder dataStr = new StringBuilder(new SimpleDateFormat("yyyy-MM-dd").format(data));
        StringBuilder hoje = new StringBuilder(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        return dataStr.toString().equals(hoje.toString());
    }

    public static String retiraCaracteresEspeciais(String stringFonte) {
        String passa = stringFonte;
        passa = passa.replaceAll("[ÂÀÁÄÃ]", "A");
        passa = passa.replaceAll("[âãàáä]", "a");
        passa = passa.replaceAll("[ÊÈÉË]", "E");
        passa = passa.replaceAll("[êèéë]", "e");
        passa = passa.replaceAll("ÎÍÌÏ", "I");
        passa = passa.replaceAll("îíìï", "i");
        passa = passa.replaceAll("[ÔÕÒÓÖ]", "O");
        passa = passa.replaceAll("[ôõòóö]", "o");
        passa = passa.replaceAll("[ÛÙÚÜ]", "U");
        passa = passa.replaceAll("[ûúùü]", "u");
        passa = passa.replaceAll("Ç", "C");
        passa = passa.replaceAll("ç", "c");
        passa = passa.replaceAll("[ýÿ]", "y");
        passa = passa.replaceAll("Ý", "Y");
        passa = passa.replaceAll("ñ", "n");
        passa = passa.replaceAll("Ñ", "N");
        passa = passa.replaceAll("  ", " ");
        passa = passa.replaceAll("[-+=*&amp;%$#@!_]", "");
        passa = passa.replaceAll("['\"]", "");
        passa = passa.replaceAll("[<>()\\{\\}]", "");
        passa = passa.replaceAll("['\\\\.,()|/]", "");
        passa = passa.replaceAll("[^!-ÿ]{1}[^ -ÿ]{0,}[^!-ÿ]{1}|[^!-ÿ]{1}", " ");
        return passa;
    }
    public static boolean isCnpjValido(String cnpj) {
        if (!cnpj.substring(0, 1).equals("")) {
            try {
                cnpj = cnpj.replace('.', ' ');//onde há ponto coloca espaço
                cnpj = cnpj.replace('/', ' ');//onde há barra coloca espaço
                cnpj = cnpj.replace('-', ' ');//onde há traço coloca espaço
                cnpj = cnpj.replaceAll(" ", "");//retira espaço
                if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111")
                        || cnpj.equals("22222222222222") || cnpj.equals("33333333333333")
                        || cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
                        || cnpj.equals("66666666666666") || cnpj.equals("77777777777777")
                        || cnpj.equals("88888888888888") || cnpj.equals("99999999999999")
                        || (cnpj.length() != 14)) {
                    return (false);
                }

                int soma = 0, dig;
                String cnpj_calc = cnpj.substring(0, 12);
                if (cnpj.length() != 14) {
                    return false;
                }
                char[] chr_cnpj = cnpj.toCharArray();
                /* Primeira parte */
                for (int i = 0; i < 4; i++) {
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                        soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(
                        dig);
                /* Segunda parte */
                soma = 0;
                for (int i = 0; i < 5; i++) {
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                        soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(
                        dig);
                return cnpj.equals(cnpj_calc);
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    public static String imprimeCPF(String CPF) {
        return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
                CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }

    public static Integer ifNullInteger(Integer value, Integer retorno) {
        if (value == null) {
            return retorno;
        }

        return retorno;
    }

    public static String ifNullString(Object str) {
        if (str == null) {
            return "";
        }
        return String.valueOf(str);
    }

    public static String getSetaMesExtenso(Integer mes) {
        String mesConvertido = String.valueOf(mes);
        String mesFinal = "";
        try {
            switch (mesConvertido) {
                case "1":
                    mesFinal = "Janeiro";
                    break;
                case "2":
                    mesFinal = "Fevereiro";
                    break;
                case "3":
                    mesFinal = "Março";
                    break;
                case "4":
                    mesFinal = "Abril";
                    break;
                case "5":
                    mesFinal = "Maio";
                    break;
                case "6":
                    mesFinal = "Junho";
                    break;
                case "7":
                    mesFinal = "Julho";
                    break;
                case "8":
                    mesFinal = "Agosto";
                    break;
                case "9":
                    mesFinal = "Setembro";
                    break;
                case "10":
                    mesFinal = "Outubro";
                    break;
                case "11":
                    mesFinal = "Novembro";
                    break;
                case "12":
                    mesFinal = "Dezembro";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mesFinal;
    }

    public static String dataExtensao(Date data){
        String dia = new SimpleDateFormat("dd").format(data);
        String mes = new SimpleDateFormat("MM").format(data);
        String ano = new SimpleDateFormat("yyyy").format(data);
        return dia+ " de "+getSetaMesExtenso(Integer.parseInt(mes)).substring(0,3)+" de "+ano;
    }

    public static String moedaBr(BigDecimal vlr){
        Locale.setDefault(new Locale("pt", "BR"));  // mudança global
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("R$ #,##0.00");
        double valorConvertido = vlr.doubleValue();
        return df.format(valorConvertido);
    }

    public static String getDiaSemana(Calendar s) {
        String diaSemana = "";
        int day = s.get(Calendar.DAY_OF_WEEK);
        try {
            if(day == Calendar.MONDAY){
                diaSemana = "SEGUNDA-FEIRA";
            }
            if(day == Calendar.TUESDAY){
                diaSemana = "TERÇA-FEIRA";
            }
            if(day == Calendar.WEDNESDAY){
                diaSemana = "QUARTA-FEIRA";
            }
            if(day == Calendar.THURSDAY){
                diaSemana = "QUINTA-FEIRA";
            }
            if(day == Calendar.FRIDAY){
                diaSemana = "SEXTA-FEIRA";
            }
            if(day == Calendar.SATURDAY){
                diaSemana = "SÁBADO";
            }
            if(day == Calendar.SUNDAY){
                diaSemana = "DOMINGO";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diaSemana;
    }
    public static boolean getverificaFds(Calendar s) {
        int day = s.get(Calendar.DAY_OF_WEEK);
        try {
            if(day == Calendar.SUNDAY || day == Calendar.SATURDAY){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
