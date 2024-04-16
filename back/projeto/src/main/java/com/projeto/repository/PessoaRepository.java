package com.projeto.repository;

import java.util.List;

import com.projeto.model.Admin;
import com.projeto.model.Aluno;
import com.projeto.model.Pessoa;
import com.projeto.model.Professor;

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
}
