package com.projeto.repository.JDBC;

import com.projeto.model.Admin;
import com.projeto.model.Aluno;
import com.projeto.model.Pessoa;
import com.projeto.model.Professor;
import com.projeto.model.Turma;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.projeto.repository.PessoaRepository;

@Repository
public class PessoaRepositoryJDBC implements PessoaRepository {

    private final JdbcTemplate jdbcTemplate;

    public PessoaRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (nome, data_nascimento, CPF, TipoUsuario  ) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, pessoa.getNome(), pessoa.getDataNascimento(), pessoa.getCpf(),
                pessoa.getTipoUsuario());
    }

    @Override
    public void save(Admin admin) {
        String sql = "INSERT INTO admin (nome, CPF) VALUES (?, ?)";
        jdbcTemplate.update(sql, admin.getNome(), admin.getCpf());
    }

    @Override
    public void save(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, CPF) VALUES (?, ?)";
        jdbcTemplate.update(sql, aluno.getNome(), aluno.getCpf());
    }

    @Override
    public void save(Professor professor) {
        String sql = "INSERT INTO professor (nome, CPF, disciplina) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, professor.getNome(), professor.getCpf(), professor.getDisciplina());
    }

    @Override
    public void save(Turma turma) {
        String sql = "INSERT INTO turma (serie, idProfessor, disciplina) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, turma.getSerie(), turma.getIdProfessor(), turma.getDisciplina());
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
        String sql = "UPDATE pessoa SET senha = ? WHERE cpf = ?";
        jdbcTemplate.update(sql, novaSenha, cpf);
    }

    @Override
    public boolean validarSenha(String cpf, String senha) {
        String sql = "SELECT COUNT(*) FROM pessoa WHERE cpf = ? AND senha = ?";

        return jdbcTemplate.queryForObject(sql, Integer.class, cpf, senha) > 0;
    }

    @Override
    public String buscarTipoUsuarioPorCpf(String cpf) {
        String sql = "SELECT TipoUsuario FROM pessoa WHERE cpf = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, cpf);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
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
            // Retorna 0 se o CPF não for encontrado
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

    @Override
    public List<Turma> findTurmasByProfessorCpf(String cpf) {
        int idProfessor = obterIdProfessorPorCpf(cpf);
        String disciplina = obterDisciplinaPorCpf(cpf);
        String sql = "SELECT idTurma, Serie, Disciplina FROM Turma WHERE idProfessor = ?";
        List<Turma> turmas = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Turma.class), idProfessor);
        
        // Define a disciplina para cada turma
        turmas.forEach(turma -> turma.setDisciplina(disciplina));
        
        return turmas;
    }
    
    
}