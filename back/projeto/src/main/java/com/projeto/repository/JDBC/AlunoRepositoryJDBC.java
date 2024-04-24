package com.projeto.repository.JDBC;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.projeto.model.Aluno;
import com.projeto.repository.AlunoRepository;

@Repository
public class AlunoRepositoryJDBC implements AlunoRepository {

    private final JdbcTemplate jdbcTemplate;

    public AlunoRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Aluno aluno) {
        String sql = "INSERT INTO Aluno (Nome, CPF) VALUES (?, ?)";
        jdbcTemplate.update(sql, aluno.getNome(), aluno.getCpf());
    }

    @Override
    public boolean verificarExistenciaAluno(int idAluno) {
        String sql = "SELECT COUNT(*) FROM Aluno WHERE idAluno = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, idAluno);
        return count > 0;
    }

    @Override
    public int obterIdAlunoPorCpf(String cpf) {
        String sql = "SELECT idAluno FROM Aluno WHERE CPF = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public List<Aluno> buscarAlunosPorTurma(int idTurma) {
        String sql = "SELECT a.idAluno, a.nome, a.CPF FROM Aluno a " +
                "INNER JOIN Turma_Alunos ta ON a.idAluno = ta.idAluno " +
                "WHERE ta.idTurma = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Aluno.class), idTurma);
    }

    @Override
    public List<Aluno> procurarAlunosPorIdTurma(int idTurma) {
        String sql = "SELECT a.idAluno, a.nome FROM aluno a " +
                "INNER JOIN Turma_Alunos ta ON a.idAluno = ta.idAluno " +
                "WHERE ta.idTurma = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Aluno.class), idTurma);
    }

}
