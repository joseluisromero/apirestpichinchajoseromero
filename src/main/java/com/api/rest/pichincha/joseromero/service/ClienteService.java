package com.api.rest.pichincha.joseromero.service;

import com.api.rest.pichincha.joseromero.exception.ValidationException;
import com.api.rest.pichincha.joseromero.presenter.ClientePresenter;

import java.util.List;

public interface ClienteService {
ClientePresenter save(ClientePresenter clientePresenter) throws ValidationException;
ClientePresenter findByIdentificacion(String identificacion) throws ValidationException;
void deleteCliente(String identificacion)throws ValidationException;
List<ClientePresenter> getClientes();
}
