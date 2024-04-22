package com.projeto.repository;

import java.sql.Date;
import java.util.List;

import com.projeto.model.Admin;
import com.projeto.model.Aluno;
import com.projeto.model.Pessoa;
import com.projeto.model.Presenca;
import com.projeto.model.Professor;
import com.projeto.model.Turma;
import com.projeto.model.TurmaAlunos;

public interface PessoaRepository {
    boolean verificarCpf(String cpf);
    void save(Pessoa pessoa);
    void save (Admin admin);
    void save (Aluno aluno);
    void save (Professor professor);
    List<Pessoa> findAll();
    void update(String cpf, String novaSenha);
    void delete(int idPessoa);
    String buscarSenhaPorCpf(String cpf);
    void atualizarSenhaPorCpf(String cpf, String novaSenha);
    boolean validarSenha(String cpf, String senha);
    String buscarTipoUsuarioPorCpf(String cpf);
    boolean verificarCpfProfessor(String cpf);
    void save (Turma turma);
    void save (TurmaAlunos turmaAlunos);
    int obterIdProfessorPorCpf(String cpf);
    String obterDisciplinaPorCpf(String cpf);
    List<Turma> findTurmasByProfessorCpf(String cpf);
    void associarAlunoTurma(int idTurma, int idAluno);
    boolean verificarExistenciaTurma(int idTurma);
    boolean verificarExistenciaAluno(int idAluno);
    List<Turma> findAllTurmas();
    int findIdTurmaBySerie(String serie);
    int obterIdAlunoPorCpf(String cpf);
    List<Turma> findTurmasByAlunoCpf(String cpf);
    List<Aluno> buscarAlunosPorTurma(int idTurma);
    void save(Presenca presenca);
    List<Aluno> procurarAlunosPorIdTurma(int idTurma);
    boolean existeTurma(int idTurma);
    boolean existeChamadaParaData(int idTurma, Date dataPresenca);
    List<Presenca> buscarPresencasDoAlunoAtual(int idAluno);
}
