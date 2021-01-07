package com.bolsadeideas.springdatajpa.controllers;

import com.bolsadeideas.springdatajpa.models.services.IClienteService;
import com.bolsadeideas.springdatajpa.view.xml.ClienteList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    @Autowired
    IClienteService clienteService;

    //METODO API REST
    @GetMapping("/listar")
    public ClienteList /*List<Cliente>*/ listar(){

        return new ClienteList(clienteService.findAll());
    }
}
