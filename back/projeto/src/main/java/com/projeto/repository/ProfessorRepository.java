package com.projeto.repository;

import java.util.List;
import com.projeto.model.Professor;

public interface ProfessorRepository {

    void save(Professor professor);
    List<Professor> findAll();
    void update (int idProfessor,Professor professor);
    void delete (int idProfessor);
}
