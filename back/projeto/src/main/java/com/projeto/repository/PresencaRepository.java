package com.projeto.repository;

import java.util.List;
import com.projeto.model.Presenca;

public interface PresencaRepository {

    void save(Presenca presenca);
    List<Presenca> findAll();
    void update (int idPresenca, Presenca presenca);
    void delete (int idPresenca);


}
