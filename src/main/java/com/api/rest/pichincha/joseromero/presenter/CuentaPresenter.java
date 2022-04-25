package com.api.rest.pichincha.joseromero.presenter;

import com.sun.istack.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cuentaId")
@ToString(of = "cuentaId")
@Data
public class CuentaPresenter {
    private UUID cuentaId;
    @NotNull
    private String numeroCuenta;
    @NotNull
    private String tipoCuenta;
    @NotNull
    @Builder.Default
    private BigDecimal saldoInicial=BigDecimal.ZERO;
    @NotNull
    @Builder.Default
    private Boolean estado=true;
    @Builder.Default
    private  BigDecimal limiteDiario =BigDecimal.ZERO;

    @NotNull
    private ClientePresenter cliente;
}
