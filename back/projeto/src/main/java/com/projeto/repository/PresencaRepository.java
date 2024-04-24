package com.projeto.repository;

import java.sql.Date;
import java.util.List;

import com.projeto.model.Presenca;

public interface PresencaRepository {
    
    void save(Presenca presenca);
    
    boolean existeChamadaParaData(int idTurma, Date dataPresenca);

    List<Presenca> buscarPresencasDoAlunoAtual(int idAluno);
}
