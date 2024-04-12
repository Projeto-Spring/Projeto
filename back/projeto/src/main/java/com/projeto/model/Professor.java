package com.projeto.model;

import java.sql.Timestamp;

public class Professor extends Pessoa {
    private Long idProfessor;
    private String disciplina;

    public Professor(Long idPessoa, String nome, Timestamp dataNascimento, String cpf, Long idProfessor, String disciplina) {
        super(idPessoa, nome, dataNascimento, cpf);
        this.idProfessor = idProfessor;
        this.disciplina = disciplina;
    }

    public Long getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Long idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }
}
