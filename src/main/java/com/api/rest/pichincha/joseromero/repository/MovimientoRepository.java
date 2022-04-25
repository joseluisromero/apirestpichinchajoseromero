package com.api.rest.pichincha.joseromero.repository;


import com.api.rest.pichincha.joseromero.entity.Movimiento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface MovimientoRepository extends CrudRepository<Movimiento, UUID> {

    @Query("SELECT m FROM Movimiento m join m.cuenta c " +
            " WHERE c.numeroCuenta=:numeroCuenta ORDER BY m.fecha DESC ")
    List<Movimiento> findByNumeroCuentaAndTop1(String numeroCuenta);

    @Query("SELECT coalesce(sum(m.valor),0.00) FROM Movimiento m join m.cuenta c " +
            " WHERE c.numeroCuenta=:numeroCuenta and m.tipoMovimiento='Retiro' and  cast(m.fecha as date)=:fecha ")
    BigDecimal findMaxRetiro(String numeroCuenta, Date fecha);

    @Query("SELECT m FROM Movimiento m join m.cuenta c join c.cliente cl join cl.persona p " +
            " WHERE p.identificacion=:identificacion " +
            " and (cast(m.fecha as string) between coalesce(cast(:initDate as string), cast(m.fecha as string)) " +
            " and coalesce(cast(:endDate as string), cast(m.fecha as string))) ORDER BY m.fecha DESC ")
    List<Movimiento> findByIdentificacionAndBetween(@Param("identificacion") String identificacion,
                                                    @Param("initDate") Date initDate,
                                                    @Param("endDate") Date endDate);

}
