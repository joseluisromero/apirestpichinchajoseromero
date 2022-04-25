package com.api.rest.pichincha.joseromero.repository;


import com.api.rest.pichincha.joseromero.entity.Cuenta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CuentaRepository extends CrudRepository<Cuenta, UUID> {
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
}
