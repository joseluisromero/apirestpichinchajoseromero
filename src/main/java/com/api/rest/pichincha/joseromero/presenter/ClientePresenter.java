package com.api.rest.pichincha.joseromero.presenter;

import com.sun.istack.NotNull;
import lombok.*;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "clienteId")
@ToString(of = "clienteId")
@Data
public class ClientePresenter {
    private UUID clienteId;
    @NotNull
    private String password;
    @NotNull
    private Boolean estado;
    @NotNull
    private PersonaPresenter persona;
}
