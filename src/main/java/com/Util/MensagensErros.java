package com.Util;

public class MensagensErros {

    private static String msgErro;
    private static boolean erro;

    public static String getMsgErro() {
        return msgErro;
    }

    public static void setMsgErro(String msgErro) {
        MensagensErros.msgErro = msgErro;
    }

    public static boolean isErro() {
        return erro;
    }

    public static void setErro(boolean erro) {
        MensagensErros.erro = erro;
    }


    public static void erroNullPointer(){
        setErro(true);
        setMsgErro("Ocorreu um erro interno no servidor");
    }
    public static void erroConstrain(){
        setErro(true);
        setMsgErro("Ocorreu um erro");
    }

    public static void erroHibernateJdbc(){
        setErro(true);
        setMsgErro("Ocorreu um erro");
    }

    public static void erroHibernate(){
        setErro(true);
        setMsgErro("Ocorreu um erro");
    }

    public static void erroGenericos(){
        setErro(true);
        setMsgErro("Ocorreu um erro");
    }
}
