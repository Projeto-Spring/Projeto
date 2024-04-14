package com.projeto.repository;

import java.util.List;
import com.projeto.model.Admin;

public interface AdminRepository {

    void save(Admin admin);
    List<Admin> findAll();
    void update (int idAdmin, Admin admin);
    void delete (int idAdmin);

    
}
