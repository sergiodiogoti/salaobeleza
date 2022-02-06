package com.Bean;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * @author Sergio
 */

@Entity
@Table(name = "usuario")
public class Usuario implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuCodigo", nullable = false)
    private Integer usuCodigo;

    @Column(name = "usuNome")
    private String usuNome;

    @Column(name = "usuLogin")
    private String usuLogin;

    @Column(name = "usuEmail")
    private String usuEmail;

    @Column(name = "usuCPF")
    private String usuCPF;

    @Column(name = "usuTipo")
    private String usuTipo;

    @Column(name = "usuSenha")
    private String usuSenha;

    @Column(name = "usuFuncao")
    private String usuFuncao;

    @Column(name = "dataHoraAtualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraAtualizacao;

    @Column(name = "usuConfirmaSenha")
    private String usuConfirmaSenha;

    @Column(name = "usuSexo")
    private String usuSexo;

    @Column(name = "usuTelefone")
    private String usuTelefone;

    @Column(name = "usuDataNasc")
    @Temporal(TemporalType.DATE)
    private Date usuDataNasc;

    @Column(name = "usuNovaSenha")
    private String usuNovaSenha;

    @Column(name = "usuSenhaAntiga")
    private String usuSenhaAntiga;

    @Column(name = "usuTema")
    private String usuTema;

    @ManyToOne
    @JoinColumn(name = "usuProCodigo", referencedColumnName = "proCodigo", nullable = true)
    private Profissional usuProCodigo;

    @ManyToOne
    @JoinColumn(name = "usuCliCodigo", referencedColumnName = "cliCodigo", nullable = true)
    private Cliente usuCliCodigo;

    public Usuario() {
        setDataHoraAtualizacao(new Date());
    }

    public Integer getUsuCodigo() {
        return usuCodigo;
    }
    public void setUsuCodigo(Integer usuCodigo) {
        this.usuCodigo = usuCodigo;
    }
    public String getUsuNome() {
        return usuNome;
    }
    public void setUsuNome(String usuNome) {
        this.usuNome = usuNome;
    }
    public String getUsuLogin() {
        return usuLogin;
    }
    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }
    public String getUsuEmail() {
        return usuEmail;
    }
    public void setUsuEmail(String usuEmail) {
        this.usuEmail = usuEmail;
    }
    public String getUsuSenha() {
        return usuSenha;
    }
    public void setUsuSenha(String usuSenha) {
        this.usuSenha = usuSenha;
    }
    public Date getDataHoraAtualizacao() {
        return dataHoraAtualizacao;
    }
    public void setDataHoraAtualizacao(Date dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }
    public String getUsuConfirmaSenha() {
        return usuConfirmaSenha;
    }
    public void setUsuConfirmaSenha(String usuConfirmaSenha) {
        this.usuConfirmaSenha = usuConfirmaSenha;
    }
    public String getUsuSexo() {
        return usuSexo;
    }
    public void setUsuSexo(String usuSexo) {
        this.usuSexo = usuSexo;
    }
    public String getUsuTelefone() {
        return usuTelefone;
    }
    public void setUsuTelefone(String usuTelefone) {
        this.usuTelefone = usuTelefone;
    }
    public Date getUsuDataNasc() {
        return usuDataNasc;
    }
    public void setUsuDataNasc(Date usuDataNasc) {
        this.usuDataNasc = usuDataNasc;
    }
    public String getUsuNovaSenha() {
        return usuNovaSenha;
    }
    public void setUsuNovaSenha(String usuNovaSenha) {
        this.usuNovaSenha = usuNovaSenha;
    }
    public String getUsuSenhaAntiga() {
        return usuSenhaAntiga;
    }
    public void setUsuSenhaAntiga(String usuSenhaAntiga) {
        this.usuSenhaAntiga = usuSenhaAntiga;
    }
    public String getUsuTema() {
        return usuTema;
    }
    public void setUsuTema(String usuTema) {
        this.usuTema = usuTema;
    }
    public Profissional getUsuProCodigo() {
        return usuProCodigo;
    }

    public String getUsuTipo() {
        return usuTipo;
    }

    public void setUsuTipo(String usuTipo) {
        this.usuTipo = usuTipo;
    }

    public String getUsuCPF() {
        return usuCPF;
    }

    public void setUsuCPF(String usuCPF) {
        this.usuCPF = usuCPF;
    }

    public String getUsuFuncao() {
        return usuFuncao;
    }

    public void setUsuFuncao(String usuFuncao) {
        this.usuFuncao = usuFuncao;
    }

    public void setUsuProCodigo(Profissional usuProCodigo) {
        this.usuProCodigo = usuProCodigo;
    }
    public Cliente getUsuCliCodigo() {
        return usuCliCodigo;
    }
    public void setUsuCliCodigo(Cliente usuCliCodigo) {
        this.usuCliCodigo = usuCliCodigo;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.usuCodigo != other.usuCodigo && (this.usuCodigo == null || !this.usuCodigo.equals(other.usuCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.usuCodigo != null ? this.usuCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return usuCodigo + "";
    }
}