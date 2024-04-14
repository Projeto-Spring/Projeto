package com.projeto.repository.JDBC;

import com.projeto.model.Admin;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.projeto.repository.AdminRepository;

@Repository
public class AdminRepositoryJDBC implements AdminRepository{

    @SuppressWarnings("unused")
    private final JdbcTemplate jdbcTemplate;

    public AdminRepositoryJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Admin admin){
 
    }

    @Override
    public List<Admin> findAll() {
        return null;

    }

    @Override
    public void update(int IdAdmin, Admin admin) {

    }

    @Override
    public void delete(int idAdmin) {

    }
}
