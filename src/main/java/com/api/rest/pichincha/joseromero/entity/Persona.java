package com.api.rest.pichincha.joseromero.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persona")
@EqualsAndHashCode(of = "personaId")
@ToString(of = "personaId")
@Data
public class Persona {
    @Id
    @GeneratedValue
    @Column(name ="persona_id")
    private UUID personaId;
    @NotNull
    @Column(name ="identificacion" ,unique = true)
    private String identificacion;
    @NotNull
    @Column(name ="nombre")
    private String nombre;
    @NotNull
    @Column(name ="genero")
    private String genero;
    @NotNull
    @Column(name ="edad")
    private Integer edad;
    @NotNull
    @Column(name ="telefono")
    private String telefono;
    @NotNull
    @Column(name ="direccion")
    private String direccion;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "persona", fetch = FetchType.LAZY)
    private Cliente cliente;

}
