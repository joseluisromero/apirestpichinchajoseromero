package com.api.rest.pichincha.joseromero.repository;


import com.api.rest.pichincha.joseromero.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, UUID> {

    @Query("SELECT c FROM Cliente c JOIN c.persona p WHERE p.identificacion=:identificacion")
    Optional<Cliente> findByIdentificacion(String identificacion);
}
