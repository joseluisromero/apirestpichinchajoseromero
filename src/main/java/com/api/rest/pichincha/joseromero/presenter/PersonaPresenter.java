package com.api.rest.pichincha.joseromero.presenter;

import com.sun.istack.NotNull;
import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "personaId")
@ToString(of = "personaId")
@Data
public class PersonaPresenter {

    private UUID personaId;
    @NotNull
    private String identificacion;
    @NotNull
    private String nombre;
    @NotNull
    private String genero;
    @NotNull
    private Integer edad;
    @NotNull
    private String telefono;
    @NotNull
    private String direccion;
}
