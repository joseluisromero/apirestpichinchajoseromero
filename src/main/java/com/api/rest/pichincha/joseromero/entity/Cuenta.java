package com.api.rest.pichincha.joseromero.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cuenta")
@EqualsAndHashCode(of = "cuentaId")
@ToString(of = "cuentaId")
@Data
public class Cuenta {
    @Id
    @GeneratedValue
    @Column(name ="cuenta_id")
    private UUID cuentaId;
    @NotNull
    @Column(name ="numero_cuenta" ,unique = true)
    private String numeroCuenta;
    @NotNull
    @Column(name ="tipo_cuenta")
    private String tipoCuenta;
    @NotNull
    @Column(name ="saldo_inicial")
    private BigDecimal saldoInicial;
    @NotNull
    @Column(name ="estado")
    @Builder.Default
    private Boolean estado=true;
    @Column(name ="limite_diario")
    private BigDecimal limiteDiario;
    @NotNull
    @JoinColumn(name = "cliente_id")
    @ManyToOne
    private Cliente cliente;
}
