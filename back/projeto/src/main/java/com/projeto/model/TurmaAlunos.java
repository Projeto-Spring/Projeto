package com.projeto.model;

// Classe TurmaAlunos
public class TurmaAlunos {
    private int idTurma;
    private int idAluno;

    // Construtor
    public TurmaAlunos(int idTurma, int idAluno) {
        this.idTurma = idTurma;
        this.idAluno = idAluno;
    }

    // Métodos getters e setters
    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }
}