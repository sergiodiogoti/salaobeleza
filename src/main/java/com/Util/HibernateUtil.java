package com.Util;

import com.Bean.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil implements java.io.Serializable {
    private  static  final long serialVersionUID = 1L;

    private static final SessionFactory sessionFactory = buildSessionFactory();

    public HibernateUtil(){

    }
    private  static  SessionFactory buildSessionFactory(){
        Configuration conf = new Configuration().configure("hibernate.cfg.xml");
        try {
            return conf.addAnnotatedClass(Usuario.class).addAnnotatedClass(Cliente.class)
.addAnnotatedClass(Profissional.class)
.addAnnotatedClass(Usuario.class)
.addAnnotatedClass(Usuario.class)
.addAnnotatedClass(Servico.class)
.addAnnotatedClass(Servico.class)
.addAnnotatedClass(Servico.class)
.addAnnotatedClass(Agendamento.class)
.addAnnotatedClass(HorarioTrabalho.class)
.addAnnotatedClass(Agendamento.class)
.addAnnotatedClass(ItensAgendamento.class)
.buildSessionFactory();
        }catch (Throwable ex){
            System.err.println("Initial SessionFactory creation failed."+ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public  static SessionFactory getSessionFactory(){return sessionFactory;}
}


