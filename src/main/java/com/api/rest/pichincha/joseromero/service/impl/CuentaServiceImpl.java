package com.api.rest.pichincha.joseromero.service.impl;


import com.api.rest.pichincha.joseromero.entity.Cliente;
import com.api.rest.pichincha.joseromero.entity.Cuenta;
import com.api.rest.pichincha.joseromero.entity.Persona;
import com.api.rest.pichincha.joseromero.exception.ValidationException;
import com.api.rest.pichincha.joseromero.presenter.ClientePresenter;
import com.api.rest.pichincha.joseromero.presenter.CuentaPresenter;
import com.api.rest.pichincha.joseromero.presenter.PersonaPresenter;
import com.api.rest.pichincha.joseromero.repository.CuentaRepository;
import com.api.rest.pichincha.joseromero.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;
    
    @Override
    public CuentaPresenter save(CuentaPresenter cuentaPresenter) throws ValidationException {
        Cuenta cuenta = new Cuenta();
        Cliente cliente=new Cliente();
        if (cuentaPresenter.getCuentaId() != null) {
            cuenta = Optional.of((cuentaRepository.findById(cuentaPresenter.getCuentaId())).orElse(new Cuenta())).get();
            cliente=cuenta.getCliente();
        } else {
            Cuenta cuenta1 = cuentaRepository.findByNumeroCuenta(cuentaPresenter.getNumeroCuenta()).orElse(new Cuenta());
            if (cuenta1.getCuentaId() != null) {
                throw new ValidationException("Cuenta ya se encuentra registrado");
            }
            cliente.setClienteId(cuentaPresenter.getCliente().getClienteId());
            cliente.setEstado(cuentaPresenter.getCliente().getEstado());
            cliente.setPassword(cuentaPresenter.getCliente().getPassword());
            cliente.setPersona(buildPersona(cuentaPresenter.getCliente().getPersona()));
        }
        cuenta.setNumeroCuenta(cuentaPresenter.getNumeroCuenta());
        cuenta.setEstado(cuentaPresenter.getEstado());
        cuenta.setTipoCuenta(cuentaPresenter.getTipoCuenta());
        cuenta.setSaldoInicial(cuenta.getCuentaId()!=null?cuenta.getSaldoInicial():cuentaPresenter.getSaldoInicial());
        cuenta.setLimiteDiario(cuentaPresenter.getLimiteDiario());
        cuenta.setCliente(cliente);
        cuenta = cuentaRepository.save(cuenta);
        CuentaPresenter cuentaPresenter1 = builCuentaPresenter(cuenta);
        return cuentaPresenter1;
    }

    @Override
    public CuentaPresenter findByNumeroCuenta(String numeroCuenta) throws ValidationException {
        Cuenta cuenta = new Cuenta();
        Optional<Cuenta> cuenta1 = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        CuentaPresenter cuentaPresenter = new CuentaPresenter();
        if (cuenta1.isPresent()) {
            cuentaPresenter = builCuentaPresenter(cuenta1.get());
        } else {
            throw new ValidationException("Cuenta no se encuentra registrado");
        }
        return cuentaPresenter;
    }

    @Override
    public void deleteCuenta(String numeroCuenta) throws ValidationException{
        Optional<Cuenta> cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta);
        if (cuenta.isPresent()) {
            cuenta.get().setEstado(false);
            cuentaRepository.delete(cuenta.get());
        }
    }

    @Override
    public List<CuentaPresenter> getCuentas() {
        Iterable iterable = cuentaRepository.findAll();
        List<CuentaPresenter> cuentas = new ArrayList<>();
        iterable.forEach(o -> {
            cuentas.add(builCuentaPresenter((Cuenta) o));
        });
        return cuentas;
    }

    private CuentaPresenter builCuentaPresenter(Cuenta cuenta) {
        return CuentaPresenter.builder().cuentaId(cuenta.getCuentaId())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipoCuenta(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldoInicial())
                .estado(cuenta.getEstado())
                .cliente(builClientePresenter(cuenta.getCliente()))
                .limiteDiario(cuenta.getLimiteDiario()).build();
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
    private Persona buildPersona(PersonaPresenter personaPresenter) {
        return Persona.builder().personaId(personaPresenter.getPersonaId())
                        .identificacion(personaPresenter.getIdentificacion())
                        .nombre(personaPresenter.getNombre())
                        .genero(personaPresenter.getGenero())
                        .edad(personaPresenter.getEdad())
                        .telefono(personaPresenter.getTelefono())
                        .direccion(personaPresenter.getDireccion()).build();
    }
}
