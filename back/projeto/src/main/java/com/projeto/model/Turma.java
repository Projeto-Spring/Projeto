package com.projeto.model;

public class Turma {
    private int idTurma;
    private String serie;
    private int idProfessor;
    private String disciplina;

    public Turma() {
    }

    public Turma(int idTurma, String serie, int idProfessor, String disciplina) {
        this.idTurma = idTurma;
        this.serie = serie;
        this.idProfessor = idProfessor;
        this.disciplina = disciplina;
    }

    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

}
