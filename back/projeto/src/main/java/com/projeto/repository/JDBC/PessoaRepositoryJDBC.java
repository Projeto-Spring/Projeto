package com.projeto.repository.JDBC;

import com.projeto.model.Pessoa;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
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

    @Override
    public String buscarSenhaPorCpf(String cpf) {
        String sql = "SELECT senha FROM pessoa WHERE cpf = ?";
        try {
            // Consulta o banco de dados para obter a senha associada ao CPF fornecido
            return jdbcTemplate.queryForObject(sql, String.class, cpf);
        } catch (EmptyResultDataAccessException e) {
            // Retorna null se não houver senha associada ao CPF
            return null;
        }
    }

}
