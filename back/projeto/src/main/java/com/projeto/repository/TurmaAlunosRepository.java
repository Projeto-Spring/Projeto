package com.projeto.repository;

import com.projeto.model.TurmaAlunos;

public interface TurmaAlunosRepository {

    void save(TurmaAlunos turmaAlunos);
    
    void associarAlunoTurma(int idTurma, int idAluno);
}
