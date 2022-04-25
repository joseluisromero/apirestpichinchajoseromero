package com.api.rest.pichincha.joseromero.presenter;

import com.sun.istack.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "movimientoId")
@ToString(of = "movimientoId")
@Data
public class MovimientoPrensenter {
    private UUID movimientoId;
    @NotNull
    private String tipoMovimiento;

    private Date fecha;
    @NotNull
    private BigDecimal valor;

    private BigDecimal saldo;

    @NotNull
    private CuentaPresenter cuenta;

}
