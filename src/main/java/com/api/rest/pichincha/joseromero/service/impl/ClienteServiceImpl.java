package com.api.rest.pichincha.joseromero.service.impl;

import com.api.rest.pichincha.joseromero.entity.Cliente;
import com.api.rest.pichincha.joseromero.entity.Persona;
import com.api.rest.pichincha.joseromero.exception.ValidationException;
import com.api.rest.pichincha.joseromero.presenter.ClientePresenter;
import com.api.rest.pichincha.joseromero.presenter.PersonaPresenter;
import com.api.rest.pichincha.joseromero.repository.ClienteRepository;
import com.api.rest.pichincha.joseromero.repository.PersonaRepository;
import com.api.rest.pichincha.joseromero.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public ClientePresenter save(ClientePresenter clientePresenter) throws ValidationException {
        Persona persona = new Persona();
        Cliente cliente = new Cliente();
        if (clientePresenter.getClienteId() != null) {
            cliente = Optional.of((clienteRepository.findById(clientePresenter.getClienteId())).orElse(new Cliente())).get();
            persona = cliente.getPersona();
        } else {
            Persona persona1 = personaRepository.findByIdentificacion(clientePresenter.getPersona().getIdentificacion()).orElse(new Persona());
            if (persona1.getPersonaId() != null) {
                throw new ValidationException("Cliente ya se encuentra registrado");
            }
        }
        cliente.setPassword(clientePresenter.getPassword());
        cliente.setEstado(clientePresenter.getEstado());
        persona.setNombre(clientePresenter.getPersona().getNombre());
        persona.setIdentificacion(clientePresenter.getPersona().getIdentificacion());
        persona.setEdad(clientePresenter.getPersona().getEdad());
        persona.setGenero(clientePresenter.getPersona().getGenero());
        persona.setTelefono(clientePresenter.getPersona().getTelefono());
        persona.setDireccion(clientePresenter.getPersona().getDireccion());
        cliente.setPersona(persona);
        cliente = clienteRepository.save(cliente);
        ClientePresenter clientePresenter1 = builClientePresenter(cliente);
        return clientePresenter1;
    }

    @Override
    public ClientePresenter findByIdentificacion(String identificacion) throws ValidationException {
        Cliente cliente = new Cliente();
        Optional<Cliente> cliente1 = clienteRepository.findByIdentificacion(identificacion);
        ClientePresenter clientePresenter = new ClientePresenter();
        if (cliente1.isPresent()) {
            clientePresenter = builClientePresenter(cliente1.get());
        } else {
            throw new ValidationException("Cliente no se encuentra registrado");
        }
        return clientePresenter;
    }

    @Override
    public void deleteCliente(String identificacion) throws ValidationException{
        Optional<Cliente> cliente1 = clienteRepository.findByIdentificacion(identificacion);
        if (cliente1.isPresent()) {
            cliente1.get().setEstado(false);
            clienteRepository.save(cliente1.get());
        }
    }

    @Override
    public List<ClientePresenter> getClientes() {
        Iterable iterable = clienteRepository.findAll();
        List<ClientePresenter> clientes = new ArrayList<>();
        iterable.forEach(o -> {
            clientes.add(builClientePresenter((Cliente) o));
        });
        return clientes;
    }

    private ClientePresenter builClientePresenter(Cliente cliente) {
        return ClientePresenter.builder().clienteId(cliente.getClienteId())
                .password(cliente.getPassword())
                .estado(cliente.getEstado())
                .persona(PersonaPresenter.builder().personaId(cliente.getPersona().getPersonaId())
                        .identificacion(cliente.getPersona().getIdentificacion())
                        .nombre(cliente.getPersona().getNombre())
                        .genero(cliente.getPersona().getGenero())
                        .edad(cliente.getPersona().getEdad())
                        .telefono(cliente.getPersona().getTelefono())
                        .direccion(cliente.getPersona().getDireccion()).build()).build();
    }
}
