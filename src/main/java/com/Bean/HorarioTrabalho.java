package com.Bean;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * @author Sergio
 */

@Entity
@Table(name = "horariotrabalho")
public class HorarioTrabalho implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "horTraCodigo", nullable = false)
    private Integer horTraCodigo;

    @ManyToOne
    @JoinColumn(name = "horTraProCodigo", referencedColumnName = "proCodigo", nullable = false)
    private Profissional horTraProCodigo;

    @Column(name = "horTraHorario")
    private String horTraHorario;

    public HorarioTrabalho() {

    }


    public Integer getHorTraCodigo() {
        return horTraCodigo;
    }

    public void setHorTraCodigo(Integer horTraCodigo) {
        this.horTraCodigo = horTraCodigo;
    }

    public Profissional getHorTraProCodigo() {
        return horTraProCodigo;
    }

    public void setHorTraProCodigo(Profissional horTraProCodigo) {
        this.horTraProCodigo = horTraProCodigo;
    }

    public String getHorTraHorario() {
        return horTraHorario;
    }

    public void setHorTraHorario(String horTraHorario) {
        this.horTraHorario = horTraHorario;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HorarioTrabalho other = (HorarioTrabalho) obj;
        if (this.horTraCodigo != other.horTraCodigo && (this.horTraCodigo == null || !this.horTraCodigo.equals(other.horTraCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.horTraCodigo != null ? this.horTraCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return horTraCodigo + "";
    }
}