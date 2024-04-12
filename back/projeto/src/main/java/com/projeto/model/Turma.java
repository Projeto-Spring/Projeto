package com.projeto.model;

import java.util.List;

// Classe Turma
public class Turma {
    private int idTurma;
    private String serie;
    private int idProfessor;

    // Construtor
    public Turma(int idTurma, String serie, int idProfessor) {
        this.idTurma = idTurma;
        this.serie = serie;
        this.idProfessor = idProfessor;
    }

    // Métodos getters e setters
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
}
