package com.api.rest.pichincha.joseromero.controller;


import com.api.rest.pichincha.joseromero.presenter.CuentaPresenter;
import com.api.rest.pichincha.joseromero.service.CuentaService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
public class CuentaController {
    
    @Autowired
    private CuentaService cuentaService;

    @PostMapping("/cuentas")
    public CuentaPresenter saveCuenta(@RequestBody CuentaPresenter cuentaPresenter) throws ValidationException {
        return cuentaService.save(cuentaPresenter);
    }

    @GetMapping("/cuentas")
    public List<CuentaPresenter> getCuentas() throws ValidationException {
        return cuentaService.getCuentas();
    }

    @GetMapping("/cuentas/numeroCuenta")
    public CuentaPresenter getCuentaNumeroCuenta(@RequestParam @NotNull String numeroCuenta) throws ValidationException {
        return cuentaService.findByNumeroCuenta(numeroCuenta);
    }

    @DeleteMapping("/cuentas-delete/numeroCuenta")
    public void deleteCuentaNumeroCuenta(@RequestParam @NotNull String numeroCuenta) throws ValidationException {
        cuentaService.deleteCuenta(numeroCuenta);
    }
    @PutMapping("/cuentas-update")
    public CuentaPresenter updateCuenta(@RequestBody CuentaPresenter cuentaPresenter) throws ValidationException {
        return cuentaService.save(cuentaPresenter);
    }
}
