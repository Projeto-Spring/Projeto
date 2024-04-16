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
    public void update(String cpf, String novaSenha) {
        String sql = "UPDATE pessoa SET senha = ? WHERE cpf = ?";
        jdbcTemplate.update(sql, novaSenha, cpf);
    }

    @Override
    public void delete(int idPessoa) {

    }

    @Override
    public boolean verificarCpf(String cpf) {
        String cpfSemFormatacao = cpf.replaceAll("[^0-9]", "");
        String sql = "SELECT COUNT(*) FROM pessoa WHERE REPLACE(REPLACE(cpf, '.', ''), '-', '') = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, cpfSemFormatacao);
        return count > 0;
    }

    @Override
    public String buscarSenhaPorCpf(String cpf) {
        String sql = "SELECT senha FROM pessoa WHERE cpf = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, cpf);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void atualizarSenhaPorCpf(String cpf, String novaSenha) {
        System.out.println("CPF: " + cpf);
        System.out.println("Senha: " + novaSenha);
        String sql = "UPDATE pessoa SET senha = ? WHERE cpf = ?";
        jdbcTemplate.update(sql, novaSenha, cpf);
    }

    @Override
    public boolean validarSenha(String cpf, String senha) {
        System.out.println("CPF: " + cpf);
        System.out.println("Senha: " + senha);
        System.out.println("Executando consulta SQL para validar senha...");
        
        // Consulta SQL para verificar se há uma correspondência de CPF e senha
        String sql = "SELECT COUNT(*) FROM pessoa WHERE cpf = ? AND senha = ?";
        System.out.println("Consulta SQL: " + sql);
    
        // Executa a consulta SQL e retorna o resultado como booleano
        // Retorna verdadeiro se houver pelo menos um resultado (ou seja, a senha é válida)
        // Retorna falso se nenhum resultado for encontrado (ou seja, a senha é inválida)
        return jdbcTemplate.queryForObject(sql, Integer.class, cpf, senha) > 0;
    }
    
    
}
