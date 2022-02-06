package com.Bean;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * @author Sergio
 */

@Entity
@Table(name = "cliente")
public class Cliente implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliCodigo", nullable = false)
    private Integer cliCodigo;

    @Column(name = "cliNome")
    private String cliNome;

    @Column(name = "cliSexo")
    private String cliSexo;

    @Column(name = "cliDataNasc")
    @Temporal(TemporalType.DATE)
    private Date cliDataNasc;

    @Column(name = "cliCPF")
    private String cliCPF;

    @Column(name = "cliCelular")
    private String cliCelular;

    @Column(name = "dataHoraAtualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraAtualizacao;

    public Cliente() {
        setDataHoraAtualizacao(new Date());
    }


    public Integer getCliCodigo() {
        return cliCodigo;
    }

    public void setCliCodigo(Integer cliCodigo) {
        this.cliCodigo = cliCodigo;
    }

    public String getCliNome() {
        return cliNome;
    }

    public void setCliNome(String cliNome) {
        this.cliNome = cliNome;
    }

    public String getCliSexo() {
        return cliSexo;
    }

    public void setCliSexo(String cliSexo) {
        this.cliSexo = cliSexo;
    }

    public String getCliCPF() {
        return cliCPF;
    }

    public void setCliCPF(String cliCPF) {
        this.cliCPF = cliCPF;
    }

    public String getCliCelular() {
        return cliCelular;
    }

    public void setCliCelular(String cliCelular) {
        this.cliCelular = cliCelular;
    }

    public Date getCliDataNasc() {
        return cliDataNasc;
    }

    public void setCliDataNasc(Date cliDataNasc) {
        this.cliDataNasc = cliDataNasc;
    }

    public Date getDataHoraAtualizacao() {
        return dataHoraAtualizacao;
    }

    public void setDataHoraAtualizacao(Date dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (this.cliCodigo != other.cliCodigo && (this.cliCodigo == null || !this.cliCodigo.equals(other.cliCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.cliCodigo != null ? this.cliCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return cliCodigo + "";
    }
}