package com.projeto.model;

import java.sql.Date;

public class Pessoa {
    private int idPessoa;
    private String nome;
    private Date dataNascimento;
    private String cpf;
    private String tipoUsuario;
    private String senha;

    public Pessoa(int idPessoa, String nome, Date dataNascimento, String cpf, String tipoUsuario, String senha) {
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.tipoUsuario = tipoUsuario;
        this.senha = senha;
    }

    public Pessoa() {
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
