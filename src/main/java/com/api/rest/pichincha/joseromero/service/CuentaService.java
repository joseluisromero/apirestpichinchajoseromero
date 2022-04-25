package com.api.rest.pichincha.joseromero.service;


import com.api.rest.pichincha.joseromero.exception.ValidationException;
import com.api.rest.pichincha.joseromero.presenter.CuentaPresenter;

import java.util.List;

public interface CuentaService {
CuentaPresenter save(CuentaPresenter cuentaPresenter) throws ValidationException;
CuentaPresenter findByNumeroCuenta(String numeroCuenta) throws ValidationException;
void deleteCuenta(String numeroCuenta)throws ValidationException;
List<CuentaPresenter> getCuentas();
}
