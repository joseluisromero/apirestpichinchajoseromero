package com.api.rest.pichincha.joseromero.presenter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "movimientoId")
@Data
public class MovimientoResponsePrensenter implements Serializable {

    private UUID movimientoId;
    @NotNull
    private String tipo;
    @NotNull
    private String cliente;
    @NotNull
    private String numeroCuenta;
    @NotNull
    private Date fecha;
    @NotNull
    private BigDecimal saldoDisponible;
    @NotNull
    private BigDecimal saldoInicial;
    @NotNull
    private BigDecimal movimiento;
    @NotNull
    private Boolean estado;

}
