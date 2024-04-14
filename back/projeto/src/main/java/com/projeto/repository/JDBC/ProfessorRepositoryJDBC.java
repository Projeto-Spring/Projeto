package com.projeto.repository.JDBC;

import com.projeto.model.Professor;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.projeto.repository.ProfessorRepository;

@Repository
public class ProfessorRepositoryJDBC implements ProfessorRepository{

    @SuppressWarnings("unused")
    private final JdbcTemplate jdbcTemplate;

    public ProfessorRepositoryJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Professor professor){
 
    }

    @Override
    public List<Professor> findAll() {
        return null;

    }

    @Override
    public void update(int idProfessor, Professor professor) {

    }

    @Override
    public void delete(int idProfessor) {

    }
}
