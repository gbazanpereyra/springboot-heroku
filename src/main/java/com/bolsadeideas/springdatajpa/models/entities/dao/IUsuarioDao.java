package com.bolsadeideas.springdatajpa.models.entities.dao;

import com.bolsadeideas.springdatajpa.models.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

    public Usuario findByUsername(String username);
}
