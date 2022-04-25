package com.api.rest.pichincha.joseromero.service.impl;

import com.api.rest.pichincha.joseromero.entity.Cliente;
import com.api.rest.pichincha.joseromero.exception.ValidationException;
import com.api.rest.pichincha.joseromero.presenter.ClientePresenter;
import com.api.rest.pichincha.joseromero.repository.ClienteRepository;
import com.api.rest.pichincha.joseromero.repository.PersonaRepository;
import com.api.rest.pichincha.joseromero.util.DatosPrueba;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @InjectMocks
    private ClienteServiceImpl clienteService;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private PersonaRepository personaRepository;

    private DatosPrueba datosPrueba;

    @BeforeEach
    public void setUp() {
        datosPrueba = new DatosPrueba();
    }

    @Test
    public void save() {
        ClientePresenter clientePresenter = datosPrueba.getClientePresenter();
        Cliente cliente = datosPrueba.getBuildCliente(clientePresenter);

        when(clienteRepository.findById(any())).thenReturn(Optional.of(datosPrueba.getBuildCliente(clientePresenter)));
        when(clienteRepository.save(any())).thenReturn(cliente);

        ClientePresenter clientePresenter1= clienteService.save(clientePresenter);

        verify(clienteRepository).save(any());
    }
    @Test
    public void shouldSaveNewCliente() {
        ClientePresenter clientePresenter = datosPrueba.getClientePresenter();
        Cliente cliente = datosPrueba.getBuildCliente(clientePresenter);
        clientePresenter.setClienteId(null);
        clientePresenter.getPersona().setPersonaId(null);


        when(personaRepository.findByIdentificacion(any())).thenReturn(Optional.empty());
        when(clienteRepository.save(any())).thenReturn(cliente);

        ClientePresenter clientePresenter1= clienteService.save(clientePresenter);

        verify(clienteRepository).save(any());
    }

    @Test
    public void shouldValidateBeforeSaveNewCliente() {
        ClientePresenter clientePresenter = datosPrueba.getClientePresenter();
        Cliente cliente = datosPrueba.getBuildCliente(clientePresenter);
        clientePresenter.setClienteId(null);
        clientePresenter.getPersona().setPersonaId(null);


        when(personaRepository.findByIdentificacion(any())).thenReturn(Optional.of(cliente.getPersona()));
        


        ValidationException exception = catchThrowableOfType(() ->
                clienteService.save(clientePresenter), ValidationException.class);
        assertNotNull(exception);
        assertEquals("Cliente ya se encuentra registrado", exception.getMessage());
    }


    @Test
    public void findByIdentificacion() {
    }

    @Test
    public void deleteCliente() {
    }

    @Test
    public void getClientes() {
    }
}