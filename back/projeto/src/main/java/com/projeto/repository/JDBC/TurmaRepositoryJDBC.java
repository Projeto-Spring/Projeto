package com.projeto.repository.JDBC;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.projeto.model.Turma;
import com.projeto.repository.TurmaRepository;

@Repository
public class TurmaRepositoryJDBC implements TurmaRepository {

    private final JdbcTemplate jdbcTemplate;

    public TurmaRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Turma turma) {
        String sql = "INSERT INTO turma (serie, idProfessor, disciplina) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, turma.getSerie(), turma.getIdProfessor(), turma.getDisciplina());
    }

    @Override
    public int findIdTurmaBySerie(String serie) {
        String sql = "SELECT idTurma FROM Turma WHERE serie = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, serie);
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
    }

    @Override
    public boolean verificarExistenciaTurma(int idTurma) {
        String sql = "SELECT COUNT(*) FROM Turma WHERE idTurma = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, idTurma);
        return count > 0;
    }

    @Override
    public List<Turma> findAllTurmas() {
        String sql = "SELECT * FROM Turma";
        List<Turma> turmas = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Turma.class));
        return turmas;
    }

    @Override
    public List<Turma> findTurmasByProfessorCpf(String cpf) {
        String sql = "SELECT t.idTurma, t.Serie, t.idProfessor, t.Disciplina, t.disciplina AS professorDisciplina FROM Turma t "
                +
                "INNER JOIN Professor p ON t.idProfessor = p.idProfessor " +
                "WHERE p.CPF = ?";
        List<Turma> turmas = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Turma.class), cpf);
        return turmas;
    }

    @Override
    public List<Turma> findTurmasByAlunoCpf(String cpf) {
        String sql = "SELECT t.idTurma, t.Serie, t.Disciplina " +
                "FROM Turma t " +
                "INNER JOIN Turma_Alunos ta ON t.idTurma = ta.idTurma " +
                "INNER JOIN Aluno a ON ta.idAluno = a.idAluno " +
                "INNER JOIN Pessoa p ON a.CPF = p.CPF " +
                "WHERE p.CPF = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Turma.class), cpf);
    }

    @Override
    public boolean existeTurma(int idTurma) {
        String sql = "SELECT COUNT(*) FROM Turma WHERE idTurma = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, idTurma);
        return count > 0;
    }

}
