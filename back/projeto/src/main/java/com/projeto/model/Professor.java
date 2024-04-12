package com.projeto.model;

import java.sql.Timestamp;

// Classe Professor
public class Professor extends Pessoa {
    private int idProfessor;
    private String disciplina;

    // Construtor
    public Professor(int idProfessor, int idPessoa, String nome, Date dataNascimento, String cpf, String tipoUsuario, String senha, String disciplina) {
        super(idPessoa, nome, dataNascimento, cpf, tipoUsuario, senha);
        this.idProfessor = idProfessor;
        this.disciplina = disciplina;
    }

    // Métodos getters e setters
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