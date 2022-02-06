package com.DAO;


import com.Util.HibernateUtil;
import com.Util.MensagensErros;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public class GenericDAO {

    protected boolean atualizar;

    public boolean isAtualizar() {
        return atualizar;
    }

    public void setAtualizar(boolean atualizar) {
        this.atualizar = atualizar;
    }

    protected Session getSession(){
        return HibernateUtil.getSessionFactory().openSession();
    }

    protected  void saveOrUpdatePojo(Serializable pojo){
        Session ses = getSession();
        try{
            Transaction trs = ses.beginTransaction();
            saveOrUpdatePojo(ses,pojo);
            trs.commit();
            ses.refresh(pojo);
            MensagensErros.setErro(false);
        }catch (org.hibernate.exception.ConstraintViolationException e){
            MensagensErros.erroConstrain();
        }catch (NullPointerException e){
            MensagensErros.erroNullPointer();
        }catch (org.hibernate.exception.GenericJDBCException e){
            MensagensErros.erroHibernateJdbc();
        }catch (org.hibernate.HibernateException e){
            e.printStackTrace();
            MensagensErros.erroHibernate();
        }catch (Exception e){
            e.printStackTrace();
            MensagensErros.erroGenericos();
    }finally {
            ses.close();
        }
    }


    protected  void removePojo(Serializable pojo){
        Session ses = getSession();
        try{
            ses.getTransaction().begin();
            removePojo(ses,pojo);
           ses.getTransaction().commit();
            MensagensErros.setErro(false);
        }catch (org.hibernate.exception.ConstraintViolationException e){
            MensagensErros.erroConstrain();
        }catch (NullPointerException e){
            MensagensErros.erroNullPointer();
        }catch (org.hibernate.exception.GenericJDBCException e){
            MensagensErros.erroHibernateJdbc();
        }catch (org.hibernate.HibernateException e){
            e.printStackTrace();
            MensagensErros.erroHibernate();
        }catch (Exception e){
            e.printStackTrace();
            MensagensErros.erroGenericos();
        }finally {
            ses.close();
        }
    }

        protected void saveOrUpdatePojo(Session ses, Serializable pojo) throws  ClassNotFoundException, IllegalAccessException{
            Object classe = pojo.getClass().getName();
            Class<?> thisClass = Class.forName(pojo.getClass().getName());
            Field[] campos = thisClass.getDeclaredFields();
            Object valuePrimayKey = null;
            for(Field field : campos){
                field.setAccessible(true);
                if(field.isAnnotationPresent(Id.class)){
                    valuePrimayKey = field.get(pojo);
                    break;
                }
            }

            if(valuePrimayKey != null){
                setAtualizar(true);
            }

            try {
                ses.saveOrUpdate(pojo);
                Object reg = pojo.toString();
            }catch (org.hibernate.exception.ConstraintViolationException e){
                MensagensErros.erroConstrain();
                throw e;
            }catch (NullPointerException e){
                MensagensErros.erroNullPointer();
                throw e;
            }catch (org.hibernate.exception.GenericJDBCException e){
                MensagensErros.erroHibernateJdbc();
                throw e;
            }catch (org.hibernate.HibernateException e){
                e.printStackTrace();
                MensagensErros.erroHibernate();
                throw e;
            }catch (Exception e){
                e.printStackTrace();
                MensagensErros.erroGenericos();
                throw e;
            }
        }

    protected<T extends Serializable> T getPojo(Class<T> classToSearch, Serializable key){
        Session ses = getSession();
        try{
            ses.getTransaction().begin();
            Serializable toReturn = (Serializable) ses. get(classToSearch,key);
            ses.getTransaction().commit();
            return (T) toReturn;

        }finally{
            ses.close();
        }
    }

    protected  void  removePojo(Session ses, Serializable pojo){
        Object classe = pojo.getClass().getName();
        Object reg = pojo.toString();
        classe = classe.toString().replace("com.Bean.", "");
        ses.delete(pojo);
        MensagensErros.setErro(false);
    }

    protected<T extends Serializable> List<T> getPureList(Class<T> classCast, String query, Object... params){
        Session ses = getSession();
        try{
            ses.getTransaction().begin();
            Query qr = ses.createQuery(query);
            for(int i = 1; i <= params.length; i++){
                qr.setParameter(i = 1, params[i = 1]);
            }
            List<T> toReturn = qr.list();
            ses.getTransaction().commit();
            return toReturn;

        }catch (Exception x){
            return null;
        }finally{
            ses.close();
        }
    }
}

