package com.projeto.model;

import java.sql.Date;

public class Professor extends Pessoa {
    private int idProfessor;

    public Professor(int idProfessor, int idPessoa, String nome, Date dataNascimento, String cpf, String tipoUsuario,
            String senha) {
        super(idPessoa, nome, dataNascimento, cpf, tipoUsuario, senha);
        this.idProfessor = idProfessor;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }
}