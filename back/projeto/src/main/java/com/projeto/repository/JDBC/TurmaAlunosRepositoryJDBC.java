package com.projeto.repository.JDBC;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.projeto.model.TurmaAlunos;
import com.projeto.repository.TurmaAlunosRepository;

@Repository
public class TurmaAlunosRepositoryJDBC implements TurmaAlunosRepository {

    private final JdbcTemplate jdbcTemplate;

    public TurmaAlunosRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(TurmaAlunos turmaAlunos) {
        String sql = "INSERT INTO turma_alunos (idTurma, idAluno) VALUES (?, ?)";
        jdbcTemplate.update(sql, turmaAlunos.getIdTurma(), turmaAlunos.getIdAluno());
    }

    @Override
    public void associarAlunoTurma(int idTurma, int idAluno) {
        String sql = "INSERT INTO Turma_Alunos (idTurma, idAluno) " +
                "SELECT ?, ? " +
                "FROM Turma " +
                "INNER JOIN Aluno ON Turma.idTurma = ? AND Aluno.idAluno = ?";
        jdbcTemplate.update(sql, idTurma, idAluno, idTurma, idAluno);
    }

}
