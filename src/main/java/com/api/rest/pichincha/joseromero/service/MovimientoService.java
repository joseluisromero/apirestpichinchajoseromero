package com.api.rest.pichincha.joseromero.service;


import com.api.rest.pichincha.joseromero.exception.ValidationException;
import com.api.rest.pichincha.joseromero.presenter.MovimientoPrensenter;
import com.api.rest.pichincha.joseromero.presenter.MovimientoResponsePrensenter;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface MovimientoService {
    MovimientoPrensenter save(MovimientoPrensenter movimientoPresenter) throws ValidationException;

    List<MovimientoPrensenter> getMovimientos();

    List<MovimientoResponsePrensenter> findByIdentificacionAndBetween(String Identificacion, Date initDate, Date endDate);

    String getReport(String Identificacion, Date initDate, Date endDate) throws JRException, IOException;
}
