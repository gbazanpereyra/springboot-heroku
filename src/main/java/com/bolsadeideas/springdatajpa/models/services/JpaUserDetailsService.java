package com.bolsadeideas.springdatajpa.models.services;

import com.bolsadeideas.springdatajpa.models.entities.Role;
import com.bolsadeideas.springdatajpa.models.entities.Usuario;
import com.bolsadeideas.springdatajpa.models.entities.dao.IUsuarioDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioDao usuarioDao;

    private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Busco el usuario en la base de datos
        Usuario usuario = usuarioDao.findByUsername(username);

        if(usuario == null) {
            logger.error("Error login: no existe el usuario: '" + username + "'");
            throw new UsernameNotFoundException("no existe el usuario: '" + username + "'");
        }

        //Creo una list del usuario con sus roles de autorización
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role: usuario.getRoles()) {
            logger.info("Role: ".concat(role.getAuthority()));
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        if(authorities.isEmpty()) {
            logger.error("Error login: el usuario '" + username + "' no tiene roles asignados");
            throw new UsernameNotFoundException("El usuario '" + username + "' no tiene roles asignados");
        }

        //Creo un usuario de springSecurity
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities );
    }
}
