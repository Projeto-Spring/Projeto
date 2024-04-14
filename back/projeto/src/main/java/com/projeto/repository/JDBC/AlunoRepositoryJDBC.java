package com.projeto.repository.JDBC;
import com.projeto.model.Aluno;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.projeto.repository.AlunoRepository;

@Repository
public class AlunoRepositoryJDBC implements AlunoRepository{

    @SuppressWarnings("unused")
    private final JdbcTemplate jdbcTemplate;

    public AlunoRepositoryJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Aluno aluno){
 
    }

    @Override
    public List<Aluno> findAll() {
        return null;

    }

    @Override
    public void update(int idAluno, Aluno aluno) {

    }

    @Override
    public void delete(int idAluno) {

    }
}