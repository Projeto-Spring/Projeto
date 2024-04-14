package com.projeto.repository.JDBC;

import com.projeto.model.Pessoa;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.projeto.repository.PessoaRepository;

@Repository
public class PessoaRepositoryJDBC implements PessoaRepository{

    private final JdbcTemplate jdbcTemplate;

    public PessoaRepositoryJDBC(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Pessoa pessoa){
 
    }

    @Override
    public List<Pessoa> findAll() {
        return null;

    }

    @Override
    public void update(int idPessoa, Pessoa pessoa) {

    }

    @Override
    public void delete(int idPessoa) {

    }

    @Override
    public boolean verificarCpf(String cpf) {
        String sql = "SELECT COUNT(*) FROM pessoa WHERE cpf = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        return count > 0;
    }
}
