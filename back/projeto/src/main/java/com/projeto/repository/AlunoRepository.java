package com.projeto.repository;

import java.util.List;
import com.projeto.model.Aluno;

public interface AlunoRepository {

    void save(Aluno aluno);

    boolean verificarExistenciaAluno(int idAluno);

    int obterIdAlunoPorCpf(String cpf);

    List<Aluno> buscarAlunosPorTurma(int idTurma);

    List<Aluno> procurarAlunosPorIdTurma(int idTurma);
}
