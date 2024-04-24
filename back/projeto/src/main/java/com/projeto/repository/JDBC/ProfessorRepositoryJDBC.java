package com.projeto.repository.JDBC;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.projeto.model.Professor;
import com.projeto.repository.ProfessorRepository;

@Repository
public class ProfessorRepositoryJDBC implements ProfessorRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProfessorRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Professor professor) {
        String sql = "INSERT INTO professor (nome, CPF) VALUES (?, ?)";
        jdbcTemplate.update(sql, professor.getNome(), professor.getCpf());
    }

    @Override
    public boolean verificarCpfProfessor(String cpf) {
        String sql = "SELECT COUNT(*) FROM professor WHERE cpf = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        return count > 0;
    }

    @Override
    public int obterIdProfessorPorCpf(String cpf) {
        String sql = "SELECT idProfessor FROM Professor WHERE CPF = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public String obterDisciplinaPorCpf(String cpf) {
        String sql = "SELECT disciplina FROM Professor WHERE CPF = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, cpf);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
