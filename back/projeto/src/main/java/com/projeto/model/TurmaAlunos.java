package com.projeto.model;

public class TurmaAlunos {
    private int idTurma;
    private int idAluno;

    public TurmaAlunos(int idTurma, int idAluno) {
        this.idTurma = idTurma;
        this.idAluno = idAluno;
    }

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