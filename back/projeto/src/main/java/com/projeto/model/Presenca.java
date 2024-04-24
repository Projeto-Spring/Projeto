package com.projeto.model;

import java.sql.Date;

public class Presenca {
    private int idPresenca;
    private int idAluno;
    private Date dataPresenca;
    private String situacao;
    private int idTurma;

    public Presenca(int idPresenca, int idAluno, Date dataPresenca, String situacao, int idTurma) {
        this.idPresenca = idPresenca;
        this.idAluno = idAluno;
        this.dataPresenca = dataPresenca;
        this.situacao = situacao;
        this.idTurma = idTurma;
    }

    public Presenca() {
        super();
    }

    public int getIdPresenca() {
        return idPresenca;
    }

    public void setIdPresenca(int idPresenca) {
        this.idPresenca = idPresenca;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public Date getDataPresenca() {
        return dataPresenca;
    }

    public void setDataPresenca(Date dataPresenca) {
        this.dataPresenca = dataPresenca;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

}