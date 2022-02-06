package com.Bean;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * @author Sergio
 */

@Entity
@Table(name = "servico")
public class Servico implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serCodigo", nullable = false)
    private Integer serCodigo;

    @Column(name = "serDescricao")
    private String serDescricao;

    @Column(name = "serDuracao")
    private String serDuracao;

    @Column(name = "serValor")
    private Double serValor;

    public Servico() {

    }

    public Integer getSerCodigo() {
        return serCodigo;
    }

    public void setSerCodigo(Integer serCodigo) {
        this.serCodigo = serCodigo;
    }

    public String getSerDescricao() {
        return serDescricao;
    }

    public void setSerDescricao(String serDescricao) {
        this.serDescricao = serDescricao;
    }

    public String getSerDuracao() {
        return serDuracao;
    }

    public void setSerDuracao(String serDuracao) {
        this.serDuracao = serDuracao;
    }

    public Double getSerValor() {
        return serValor;
    }

    public void setSerValor(Double serValor) {
        this.serValor = serValor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Servico other = (Servico) obj;
        if (this.serCodigo != other.serCodigo && (this.serCodigo == null || !this.serCodigo.equals(other.serCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.serCodigo != null ? this.serCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return serCodigo + "";
    }
}