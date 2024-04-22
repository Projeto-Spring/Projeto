package com.projeto.model;

import java.sql.Date;

// Classe Admin
public class Admin extends Pessoa {
    private int idAdmin;

    // Construtor
    public Admin(int idAdmin, int idPessoa, String nome, Date dataNascimento, String cpf, String tipoUsuario,
            String senha) {
        super(idPessoa, nome, dataNascimento, cpf, tipoUsuario, senha);
        this.idAdmin = idAdmin;
    }

    // Métodos getters e setters
    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }
}