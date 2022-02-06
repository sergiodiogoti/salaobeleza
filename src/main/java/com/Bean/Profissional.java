package com.Bean;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
/**
 *
 * @author Sergio
 */

@Entity
@Table(name = "profissional")
public class Profissional implements java.io.Serializable {
private static final long serialVersionUID = 1L;

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="proCodigo", nullable=false)
    private Integer proCodigo;



@Column(name="proNome")
    private String proNome;



@Column(name="proSexo")
    private String proSexo;



@Column(name="proCPF")
    private String proCPF;



@Column(name="proCelular")
    private String proCelular;



@Column(name="dataHoraAtualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraAtualizacao;

public Profissional() {
        setDataHoraAtualizacao(new Date());
    }



public Integer getProCodigo() {
        return proCodigo;
    }

    public void setProCodigo(Integer proCodigo) {
        this.proCodigo = proCodigo;
    }

public String getProNome() {
        return proNome;
    }

    public void setProNome(String proNome) {
        this.proNome = proNome;
    }

public String getProSexo() {
        return proSexo;
    }

    public void setProSexo(String proSexo) {
        this.proSexo = proSexo;
    }

public String getProCPF() {
        return proCPF;
    }

    public void setProCPF(String proCPF) {
        this.proCPF = proCPF;
    }

public String getProCelular() {
        return proCelular;
    }

    public void setProCelular(String proCelular) {
        this.proCelular = proCelular;
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
        final Profissional other = (Profissional) obj;
        if (this.proCodigo != other.proCodigo && (this.proCodigo == null || !this.proCodigo.equals(other.proCodigo))) {
            return false;
        }
        return true;
    }
@Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.proCodigo != null ? this.proCodigo.hashCode() : 0);
        return hash;
    }
@Override
  public String toString(){
    return proCodigo+"";
  }
}