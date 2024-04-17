package com.projeto.model;

import java.sql.Date;

public class Aluno extends Pessoa {
    private int idAluno;
    private int idTurma;

    // Construtor
    public Aluno(int idAluno, int idPessoa, String nome, Date dataNascimento, String cpf, String tipoUsuario, String senha, int idTurma) {
        super(idPessoa, nome, dataNascimento, cpf, tipoUsuario, senha);
        this.idAluno = idAluno;
        this.idTurma = idTurma;
    }

    // Métodos getters e setters
    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }
}
