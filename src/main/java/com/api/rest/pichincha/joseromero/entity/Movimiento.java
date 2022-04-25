package com.api.rest.pichincha.joseromero.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimientos")
@EqualsAndHashCode(of = "movimientoId")
@ToString(of = "movimientoId")
@Data
public class Movimiento implements Serializable {
    @Id
    @GeneratedValue
    @Column(name ="movimiento_id")
    private UUID movimientoId;
    @NotNull
    @Column(name ="tipo_movimiento")
    private String tipoMovimiento;
    @NotNull
    @Column(name ="fecha")
    private Date fecha;
    @NotNull
    @Column(name ="valor")
    private BigDecimal valor;
    @NotNull
    @Column(name ="saldo")
    private BigDecimal saldo;

    @NotNull
    @JoinColumn(name = "cuenta_id")
    @ManyToOne
    private Cuenta cuenta;

}
