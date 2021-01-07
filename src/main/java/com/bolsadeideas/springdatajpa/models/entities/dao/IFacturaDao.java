package com.bolsadeideas.springdatajpa.models.entities.dao;

import com.bolsadeideas.springdatajpa.models.entities.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IFacturaDao extends CrudRepository<Factura, Long> {

    //Consulta con JOIN sobre todas las tablas
    @Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1")
    public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
}
