package com.projeto.repository;

import java.util.List;
import com.projeto.model.Pessoa;

public interface PessoaRepository {
    boolean verificarCpf(String cpf);
    void save(Pessoa pessoa);
    List<Pessoa> findAll();
    void update(int idPessoa, Pessoa pessoa);
    void delete(int idPessoa);
    String buscarSenhaPorCpf(String cpf); // Método adicionado
}
