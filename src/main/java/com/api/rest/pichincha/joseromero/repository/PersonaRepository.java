package com.api.rest.pichincha.joseromero.repository;

import com.api.rest.pichincha.joseromero.entity.Persona;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonaRepository extends CrudRepository<Persona, UUID> {
Optional<Persona> findByIdentificacion(String identificacion);
}
