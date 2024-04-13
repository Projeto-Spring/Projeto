package com.projeto.model;

import java.sql.Date;


public class Aluno extends Pessoa {
    private int idAluno;

    // Construtor
    public Aluno(int idAluno, int idPessoa, String nome, Date dataNascimento, String cpf, String tipoUsuario, String senha) {
        super(idPessoa, nome, dataNascimento, cpf, tipoUsuario, senha);
        this.idAluno = idAluno;
    }

    // Métodos getters e setters
    public int getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }
}