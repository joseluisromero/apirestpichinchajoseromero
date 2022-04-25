package com.api.rest.pichincha.joseromero.controller;

import com.api.rest.pichincha.joseromero.presenter.MovimientoPrensenter;
import com.api.rest.pichincha.joseromero.presenter.MovimientoResponsePrensenter;
import com.api.rest.pichincha.joseromero.service.MovimientoService;
import com.sun.istack.NotNull;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
public class MovimientoController {
    
    @Autowired
    private MovimientoService movimientoService;

    @PostMapping("/movimientos")
    public MovimientoPrensenter saveCuenta(@RequestBody MovimientoPrensenter movimientoPresenter) throws ValidationException {
        return movimientoService.save(movimientoPresenter);
    }

    @GetMapping("/movimientos")
    public List<MovimientoPrensenter> getMovimientos() throws ValidationException {
        return movimientoService.getMovimientos();
    }

    @GetMapping("/movimientos/cliente-fecha")
    public List<MovimientoResponsePrensenter> getIdentificacionAndBetween(@RequestParam @NotNull String identificacion, @RequestParam  Date initDate, @RequestParam  Date endDate) throws ValidationException {
        return movimientoService.findByIdentificacionAndBetween(identificacion,initDate,endDate);
    }

    @GetMapping("/movimientos/report/cliente-fecha")
    public String getReportIdentificacionAndBetween(@RequestParam @NotNull String identificacion, @RequestParam  Date initDate, @RequestParam  Date endDate) throws  JRException, IOException {
        return movimientoService.getReport(identificacion,initDate,endDate);
    }

}
