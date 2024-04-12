package com.projeto.model;

import java.sql.Timestamp;

public class Aluno extends Pessoa {
    private String matricula;

    // Construtor apenas com matrícula, sem idAluno
    public Aluno(Long idPessoa, String nome, Timestamp dataNascimento, String cpf, String matricula) {
        super(idPessoa, nome, dataNascimento, cpf); // Chamada ao construtor da classe base, Pessoa
        this.matricula = matricula;
    }

    // Getter para matricula
    public String getMatricula() {
        return matricula;
    }

    // Setter para matricula
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}