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
        String sql = "INSERT INTO pessoa (nome, data_nascimento, cpf, tipo_usuario, senha) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, pessoa.getNome(), pessoa.getDataNascimento(), pessoa.getCpf(), pessoa.getTipoUsuario(), pessoa.getSenha());
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
        // Remover pontos, traços e outros caracteres não numéricos do CPF
        String cpfSemFormatacao = cpf.replaceAll("[^0-9]", "");
    
        String sql = "SELECT COUNT(*) FROM pessoa WHERE REPLACE(REPLACE(cpf, '.', ''), '-', '') = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, cpfSemFormatacao);
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
