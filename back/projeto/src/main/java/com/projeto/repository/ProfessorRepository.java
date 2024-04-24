package com.projeto.repository;

import com.projeto.model.Professor;

public interface ProfessorRepository {
    
    void save(Professor professor);

    boolean verificarCpfProfessor(String cpf);

    int obterIdProfessorPorCpf(String cpf);

    String obterDisciplinaPorCpf(String cpf);

}
