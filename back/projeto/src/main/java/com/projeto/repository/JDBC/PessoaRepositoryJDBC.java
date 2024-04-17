package com.projeto.repository.JDBC;

import com.projeto.model.Admin;
import com.projeto.model.Aluno;
import com.projeto.model.Pessoa;
import com.projeto.model.Professor;
import com.projeto.model.Turma;
import com.projeto.model.TurmaAlunos;

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

    // Implemente o método findIdTurmaBySerie na classe PessoaRepositoryJDBC
    @Override
    public int findIdTurmaBySerie(String serie) {
        String sql = "SELECT idTurma FROM Turma WHERE serie = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, serie);
        } catch (EmptyResultDataAccessException e) {
            return -1; // Ou lidar com o caso em que nenhuma turma é encontrada para a série fornecida
        }
    }

    // Atualize o método save na classe PessoaRepositoryJDBC para atribuir o idTurma
    // ao aluno
    @Override
    public void save(Aluno aluno) {
        String sql = "INSERT INTO Aluno (Nome, CPF) VALUES (?, ?)";
        jdbcTemplate.update(sql, aluno.getNome(), aluno.getCpf());
    }    

    @Override
    public void save(Professor professor) {
        String sql = "INSERT INTO professor (nome, CPF) VALUES (?, ?)";
        jdbcTemplate.update(sql, professor.getNome(), professor.getCpf());
    }

    @Override
    public void save(Turma turma) {
        String sql = "INSERT INTO turma (serie, idProfessor, disciplina) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, turma.getSerie(), turma.getIdProfessor(), turma.getDisciplina());
    }
    @Override
    public void save(TurmaAlunos turmaAlunos) {
        String sql = "INSERT INTO turma_alunos (idTurma, idAluno) VALUES (?, ?)";
        jdbcTemplate.update(sql, turmaAlunos.getIdTurma(), turmaAlunos.getIdAluno());
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
        String sql = "SELECT t.idTurma, t.Serie, t.idProfessor, t.Disciplina, p.disciplina AS professorDisciplina FROM Turma t "
                +
                "INNER JOIN Professor p ON t.idProfessor = p.idProfessor " +
                "WHERE p.CPF = ?";
        List<Turma> turmas = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Turma.class), cpf);
        return turmas;
    }

    @Override
    public void associarAlunoTurma(int idTurma, int idAluno) {
        String sql = "INSERT INTO Turma_Alunos (idTurma, idAluno) " +
                "SELECT ?, ? " +
                "FROM Turma " +
                "INNER JOIN Aluno ON Turma.idTurma = ? AND Aluno.idAluno = ?";
        jdbcTemplate.update(sql, idTurma, idAluno, idTurma, idAluno);
    }

    @Override
    public boolean verificarExistenciaAluno(int idAluno) {
        String sql = "SELECT COUNT(*) FROM Aluno WHERE idAluno = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, idAluno);
        return count > 0;
    }

    @Override
    public boolean verificarExistenciaTurma(int idTurma) {
        String sql = "SELECT COUNT(*) FROM Turma WHERE idTurma = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, idTurma);
        return count > 0;
    }

    @Override
    public List<Turma> findAllTurmas() {
        String sql = "SELECT * FROM Turma";
        List<Turma> turmas = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Turma.class));
        return turmas;
    }
    @Override
    public int obterIdAlunoPorCpf(String cpf) {
        String sql = "SELECT idAluno FROM Aluno WHERE CPF = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        } catch (EmptyResultDataAccessException e) {
            // Retorna 0 se o CPF não for encontrado
            return 0;
        }
    }
}