package com.api.rest.pichincha.joseromero.util;

import com.api.rest.pichincha.joseromero.entity.Cliente;
import com.api.rest.pichincha.joseromero.entity.Persona;
import com.api.rest.pichincha.joseromero.presenter.ClientePresenter;
import com.api.rest.pichincha.joseromero.presenter.PersonaPresenter;

import java.util.UUID;

public class DatosPrueba {

    public ClientePresenter getClientePresenter(){
        return ClientePresenter.builder().clienteId(UUID.randomUUID())
                .estado(true)
                .persona(getPersonaPresenter())
                .password("1234").build();
    }
    public PersonaPresenter getPersonaPresenter(){
        return PersonaPresenter.builder().personaId(UUID.randomUUID())
                .edad(24)
                .genero("Masculino")
                .nombre("Jose")
                .identificacion("1234567892")
                .telefono("09999999").direccion("Joaquin gutierrez y abelardo andrade").build();
    }

    public Cliente getBuildCliente(ClientePresenter clientePresenter){
        Cliente cliente=new Cliente();
        cliente.setClienteId(clientePresenter.getClienteId());
        cliente.setEstado(clientePresenter.getEstado());
        cliente.setPassword(clientePresenter.getPassword());
        cliente.setPersona(getBuildPersona(clientePresenter.getPersona()));
        return cliente;
    }
    public Persona getBuildPersona(PersonaPresenter personaPresenter){
        Persona persona=new Persona();
        persona.setPersonaId(personaPresenter.getPersonaId());
        persona.setEdad(personaPresenter.getEdad());
        persona.setNombre(personaPresenter.getNombre());
        persona.setGenero(personaPresenter.getGenero());
        persona.setIdentificacion(personaPresenter.getIdentificacion());
        persona.setTelefono(personaPresenter.getTelefono());
        persona.setDireccion(personaPresenter.getDireccion());
        return persona;
    }
}
