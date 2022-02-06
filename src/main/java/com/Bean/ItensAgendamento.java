package com.Bean;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * @author Sergio
 */

@Entity
@Table(name = "itensagendamento")
public class ItensAgendamento implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itAgeCodigo", nullable = false)
    private Integer itAgeCodigo;

    @ManyToOne
    @JoinColumn(name = "itAgeAgeCodigo", referencedColumnName = "ageCodigo", nullable = true)
    private Agendamento itAgeAgeCodigo;

    @ManyToOne
    @JoinColumn(name = "itAgeProCodigo", referencedColumnName = "proCodigo", nullable = true)
    private Profissional itAgeProCodigo;

    @ManyToOne
    @JoinColumn(name = "itAgeSerCodigo", referencedColumnName = "serCodigo", nullable = true)
    private Servico itAgeSerCodigo;


    @Column(name = "itAgeData")
    @Temporal(TemporalType.DATE)
    private Date itAgeData;


    @Column(name = "itAgeHora")
    private String itAgeHora;


    @Column(name = "itAgesituacao")
    private String itAgesituacao;


    @Column(name = "itAgeObservacao")
    private String itAgeObservacao;


    @Column(name = "itAgeSolicitouCancelamento")
    private String itAgeSolicitouCancelamento;


    @Column(name = "itAgeMotivo")
    private String itAgeMotivo;

    public ItensAgendamento() {

    }


    public Integer getItAgeCodigo() {
        return itAgeCodigo;
    }

    public void setItAgeCodigo(Integer itAgeCodigo) {
        this.itAgeCodigo = itAgeCodigo;
    }

    public Agendamento getItAgeAgeCodigo() {
        return itAgeAgeCodigo;
    }

    public void setItAgeAgeCodigo(Agendamento itAgeAgeCodigo) {
        this.itAgeAgeCodigo = itAgeAgeCodigo;
    }

    public Profissional getItAgeProCodigo() {
        return itAgeProCodigo;
    }

    public void setItAgeProCodigo(Profissional itAgeProCodigo) {
        this.itAgeProCodigo = itAgeProCodigo;
    }

    public Servico getItAgeSerCodigo() {
        return itAgeSerCodigo;
    }

    public void setItAgeSerCodigo(Servico itAgeSerCodigo) {
        this.itAgeSerCodigo = itAgeSerCodigo;
    }

    public Date getItAgeData() {
        return itAgeData;
    }

    public void setItAgeData(Date itAgeData) {
        this.itAgeData = itAgeData;
    }

    public String getItAgeHora() {
        return itAgeHora;
    }

    public void setItAgeHora(String itAgeHora) {
        this.itAgeHora = itAgeHora;
    }

    public String getItAgesituacao() {
        return itAgesituacao;
    }

    public void setItAgesituacao(String itAgesituacao) {
        this.itAgesituacao = itAgesituacao;
    }

    public String getItAgeObservacao() {
        return itAgeObservacao;
    }

    public void setItAgeObservacao(String itAgeObservacao) {
        this.itAgeObservacao = itAgeObservacao;
    }

    public String getItAgeSolicitouCancelamento() {
        return itAgeSolicitouCancelamento;
    }

    public void setItAgeSolicitouCancelamento(String itAgeSolicitouCancelamento) {
        this.itAgeSolicitouCancelamento = itAgeSolicitouCancelamento;
    }

    public String getItAgeMotivo() {
        return itAgeMotivo;
    }

    public void setItAgeMotivo(String itAgeMotivo) {
        this.itAgeMotivo = itAgeMotivo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItensAgendamento other = (ItensAgendamento) obj;
        if (this.itAgeCodigo != other.itAgeCodigo && (this.itAgeCodigo == null || !this.itAgeCodigo.equals(other.itAgeCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.itAgeCodigo != null ? this.itAgeCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return itAgeCodigo + "";
    }
}