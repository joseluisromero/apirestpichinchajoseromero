package com.api.rest.pichincha.joseromero.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
@EqualsAndHashCode(of = "clienteId")
@ToString(of = "clienteId")
@Data
public class Cliente{

    @Id
    @GeneratedValue
    @Column(name ="cliente_id")
    private UUID clienteId;
    @NotNull
    @Column(name ="password")
    private String password;
    @NotNull
    @Column(name ="estado")
    private Boolean estado=true;

    @MapsId
    @JoinColumn(name = "cliente_id")
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Persona persona;

}
