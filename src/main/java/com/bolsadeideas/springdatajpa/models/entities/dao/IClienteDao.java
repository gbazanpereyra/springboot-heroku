package com.bolsadeideas.springdatajpa.models.entities.dao;

import com.bolsadeideas.springdatajpa.models.entities.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

//CRUD REPOSITORY ES LA PAPA
//Indicamos como parámetros el nombre de la clase Entity y el tipo del ID
public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

    @Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
    public Cliente fetchByIdWithFacturas(Long id);
}
