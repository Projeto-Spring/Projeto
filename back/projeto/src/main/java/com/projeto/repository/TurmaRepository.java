package com.projeto.repository;

import java.util.List;

import com.projeto.model.Turma;

public interface TurmaRepository {
    
    void save(Turma turma);

    int findIdTurmaBySerie(String serie);
    
    boolean verificarExistenciaTurma(int idTurma);

    List<Turma> findAllTurmas();

    List<Turma> findTurmasByProfessorCpf(String cpf);

    List<Turma> findTurmasByAlunoCpf(String cpf);

    boolean existeTurma(int idTurma);
}
