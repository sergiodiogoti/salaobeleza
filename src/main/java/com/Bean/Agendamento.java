package com.Bean;

import com.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * @author Sergio
 */

@Entity
@Table(name = "agendamento")
public class Agendamento implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ageCodigo", nullable = false)
    private Integer ageCodigo;

    @ManyToOne
    @JoinColumn(name = "ageCliCodigo", referencedColumnName = "cliCodigo", nullable = true)
    private Cliente ageCliCodigo;


    @Column(name = "ageData")
    @Temporal(TemporalType.DATE)
    private Date ageData;


    @Column(name = "ageObservacao")
    private String ageObservacao;

    @Transient
    private int qtdeServicos;

    public Agendamento() {

    }


    public Integer getAgeCodigo() {
        return ageCodigo;
    }

    public void setAgeCodigo(Integer ageCodigo) {
        this.ageCodigo = ageCodigo;
    }

    public Cliente getAgeCliCodigo() {
        return ageCliCodigo;
    }

    public void setAgeCliCodigo(Cliente ageCliCodigo) {
        this.ageCliCodigo = ageCliCodigo;
    }

    public Date getAgeData() {
        return ageData;
    }

    public void setAgeData(Date ageData) {
        this.ageData = ageData;
    }

    public String getAgeObservacao() {
        return ageObservacao;
    }

    public void setAgeObservacao(String ageObservacao) {
        this.ageObservacao = ageObservacao;
    }

    public int getQtdeServicos() {
        try(Session ses = HibernateUtil.getSessionFactory().openSession()){
            String sql = "SELECT * FROM itensagendamento it \n" +
                         "WHERE it.itAgeAgeCodigo = "+ this.getAgeCodigo();
            Query q = ses.createNativeQuery(sql);
            if(q.list().size() > 0)
                qtdeServicos = q.list().size();
        }catch (Exception e){
            e.printStackTrace();
        }
        return qtdeServicos;
    }

    public void setQtdeServicos(int qtdeServicos) {
        this.qtdeServicos = qtdeServicos;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Agendamento other = (Agendamento) obj;
        if (this.ageCodigo != other.ageCodigo && (this.ageCodigo == null || !this.ageCodigo.equals(other.ageCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.ageCodigo != null ? this.ageCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return ageCodigo + "";
    }
}