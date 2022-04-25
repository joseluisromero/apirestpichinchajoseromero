package com.api.rest.pichincha.joseromero.controller;


import com.api.rest.pichincha.joseromero.presenter.ClientePresenter;
import com.api.rest.pichincha.joseromero.service.ClienteService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/clientes")
    public ClientePresenter saveCliente(@RequestBody ClientePresenter clientePresenter) throws ValidationException {
        return clienteService.save(clientePresenter);
    }

    @GetMapping("/clientes")
    public List<ClientePresenter> getClientes() throws ValidationException {
        return clienteService.getClientes();
    }

    @GetMapping("/clientes/identificacion")
    public ClientePresenter getClienteIdentificacion(@RequestParam @NotNull String identificacion) throws ValidationException {
        return clienteService.findByIdentificacion(identificacion);
    }

    @DeleteMapping("/clientes-delete/identificacion")
    public void deleteClienteIdentificacion(@RequestParam @NotNull String identificacion) throws ValidationException {
        clienteService.deleteCliente(identificacion);
    }
    @PutMapping("/clientes-update")
    public ClientePresenter updateCliente(@RequestBody ClientePresenter clientePresenter) throws ValidationException {
        return clienteService.save(clientePresenter);
    }
}
