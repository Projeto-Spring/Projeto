package com.projeto.repository.JDBC;

import java.sql.Date;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.projeto.model.Presenca;
import com.projeto.repository.PresencaRepository;

@Repository
public class PresencaRepositoryJDBC implements PresencaRepository {

    private final JdbcTemplate jdbcTemplate;

    public PresencaRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Presenca presenca) {
        String sql = "INSERT INTO presenca (idAluno, idTurma, data_presenca, situacao) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, presenca.getIdAluno(), presenca.getIdTurma(), presenca.getDataPresenca(),
                presenca.getSituacao());
    }

    @Override
    public boolean existeChamadaParaData(int idTurma, Date dataPresenca) {
        String sql = "SELECT COUNT(*) FROM presenca WHERE idTurma = ? AND data_presenca = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, idTurma, dataPresenca);
        return count > 0;
    }

    @Override
    public List<Presenca> buscarPresencasDoAlunoAtual(int idAluno) {
        String sql = "SELECT * FROM presenca WHERE idAluno = ? ORDER BY data_presenca";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Presenca.class), idAluno);
    }
}
