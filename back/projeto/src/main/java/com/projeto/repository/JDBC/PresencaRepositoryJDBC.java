package com.projeto.repository.JDBC;

import com.projeto.model.Presenca;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.projeto.repository.PresencaRepository;

@Repository
public class PresencaRepositoryJDBC implements PresencaRepository{

    @SuppressWarnings("unused")
    private final JdbcTemplate jdbcTemplate;

    public PresencaRepositoryJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Presenca presenca){
 
    }

    @Override
    public List<Presenca> findAll() {
        return null;

    }

    @Override
    public void update(int idPresenca, Presenca presenca) {

    }

    @Override
    public void delete(int idPresenca) {

    }
}
