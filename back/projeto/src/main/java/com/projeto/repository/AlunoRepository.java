package com.projeto.repository;

import java.util.List;
import com.projeto.model.Aluno;

public interface AlunoRepository {

    void save(Aluno aluno);
    List<Aluno> findAll();
    void update (int idAluno, Aluno aluno);
    void delete (int idAluno);
}
