package com.projeto.repository.JDBC;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.projeto.model.Admin;
import com.projeto.repository.AdminRepository;

@Repository
public class AdminRepositoryJDBC implements AdminRepository {

    private final JdbcTemplate jdbcTemplate;

    public AdminRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Admin admin) {
        String sql = "INSERT INTO admin (nome, CPF) VALUES (?, ?)";
        jdbcTemplate.update(sql, admin.getNome(), admin.getCpf());
    }

}
