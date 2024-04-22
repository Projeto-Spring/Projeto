package com.projeto.model;

import java.sql.Date;

public class Admin extends Pessoa {
    private int idAdmin;

    public Admin(int idAdmin, int idPessoa, String nome, Date dataNascimento, String cpf, String tipoUsuario,
            String senha) {
        super(idPessoa, nome, dataNascimento, cpf, tipoUsuario, senha);
        this.idAdmin = idAdmin;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }
}