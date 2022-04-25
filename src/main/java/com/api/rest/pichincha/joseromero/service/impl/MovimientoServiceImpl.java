package com.api.rest.pichincha.joseromero.service.impl;


import com.api.rest.pichincha.joseromero.entity.Cliente;
import com.api.rest.pichincha.joseromero.entity.Cuenta;
import com.api.rest.pichincha.joseromero.entity.Movimiento;
import com.api.rest.pichincha.joseromero.exception.ValidationException;
import com.api.rest.pichincha.joseromero.presenter.*;
import com.api.rest.pichincha.joseromero.repository.CuentaRepository;
import com.api.rest.pichincha.joseromero.repository.MovimientoRepository;
import com.api.rest.pichincha.joseromero.service.MovimientoService;
import com.api.rest.pichincha.joseromero.util.DateUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MovimientoServiceImpl implements MovimientoService {
    @Autowired
    private MovimientoRepository movimientoRepository;
    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public MovimientoPrensenter save(MovimientoPrensenter movimientoPresenter) throws ValidationException {
        Movimiento movimiento = new Movimiento();
        movimientoPresenter.setFecha(new Date());
        Optional<Cuenta> cuenta1 = cuentaRepository.findByNumeroCuenta(movimientoPresenter.getCuenta().getNumeroCuenta());
        if (!cuenta1.isPresent()) {
            throw new ValidationException("la cuenta " + movimientoPresenter.getCuenta().getNumeroCuenta() + " no existe ");
        }
        List<Movimiento> listMovimientoOld = movimientoRepository.findByNumeroCuentaAndTop1(movimientoPresenter.getCuenta().getNumeroCuenta());
        Optional<Movimiento> movimientoOld = listMovimientoOld.isEmpty() ? Optional.empty() : listMovimientoOld.stream().findFirst();
        BigDecimal limitDay = BigDecimal.ZERO;
        BigDecimal saldo = BigDecimal.ZERO;
        limitDay = movimientoRepository.findMaxRetiro(movimientoPresenter.getCuenta().getNumeroCuenta(), movimientoPresenter.getFecha());
        if (movimientoPresenter.getTipoMovimiento().equals("Deposito")) {
            if (movimientoOld.isPresent()) {
                saldo = movimientoOld.get().getSaldo().add(movimientoPresenter.getValor());
            } else {
                saldo = movimientoPresenter.getCuenta().getSaldoInicial().add(movimientoPresenter.getValor());
            }
            movimiento.setValor(movimientoPresenter.getValor());
        } else {
            limitDay = (limitDay.negate()).add(movimientoPresenter.getValor());
            if (movimientoOld.isPresent()) {
                if (movimientoOld.get().getSaldo().compareTo(BigDecimal.ZERO) == 0 || movimientoPresenter.getValor().compareTo(movimientoOld.get().getSaldo()) == 1) {
                    throw new ValidationException("Saldo no disponible");
                }
                if (limitDay.compareTo(movimientoPresenter.getCuenta().getLimiteDiario()) == 1) {
                    throw new ValidationException("Cupo diario Excedido");
                }
                saldo = movimientoOld.get().getSaldo().subtract(movimientoPresenter.getValor());
            } else if (movimientoPresenter.getCuenta().getSaldoInicial().compareTo(movimientoPresenter.getValor()) == 1 && limitDay.compareTo(movimientoPresenter.getCuenta().getLimiteDiario()) == 1) {
                throw new ValidationException("Cupo diario Excedido");
            } else if (movimientoPresenter.getCuenta().getSaldoInicial().compareTo(movimientoPresenter.getValor()) == 1) {
                saldo = movimientoPresenter.getCuenta().getSaldoInicial().subtract(movimientoPresenter.getValor());
            }
            movimiento.setValor(movimientoPresenter.getValor().negate());
        }
        movimiento.setTipoMovimiento(movimientoPresenter.getTipoMovimiento());
        movimiento.setCuenta(cuenta1.get());
        movimiento.setFecha(new Date());
        movimiento.setSaldo(saldo);
        movimiento = movimientoRepository.save(movimiento);
        MovimientoPrensenter movimientoPrensenter = new MovimientoPrensenter();
        movimientoPrensenter = builMovimientoPrensenter(movimiento);
        return movimientoPrensenter;
    }


    @Override
    public List<MovimientoPrensenter> getMovimientos() {
        List<MovimientoPrensenter> movimientos = new ArrayList<>();
        Iterable<Movimiento> iterable = movimientoRepository.findAll();
        iterable.forEach(movimiento -> {
            movimientos.add(builMovimientoPrensenter(movimiento));
        });
        return movimientos;
    }

    @Override
    public List<MovimientoResponsePrensenter> findByIdentificacionAndBetween(String Identificacion, Date initDate, Date endDate) {
        Date[] dates = this.validateRangeDates(initDate, endDate);
        initDate = dates[0];
        endDate = dates[1];
        List<MovimientoResponsePrensenter> movimientos = new ArrayList<>();
        Iterable<Movimiento> iterable = movimientoRepository.findByIdentificacionAndBetween(Identificacion, initDate, endDate);
        iterable.forEach(movimiento -> {
            movimientos.add(builMovimientoResponsePrensenter(movimiento));
        });
        return movimientos;
    }

    @Override
    public String getReport(String Identificacion, Date initDate, Date endDate) throws JRException, IOException {
        Date[] dates = this.validateRangeDates(initDate, endDate);
        initDate = dates[0];
        endDate = dates[1];
        List<MovimientoResponsePrensenter> movimientos = new ArrayList<>();
        Iterable<Movimiento> iterable = movimientoRepository.findByIdentificacionAndBetween(Identificacion, initDate, endDate);
        List<MovimientoResponsePrensenter> collectionDataSource =new ArrayList<>();
        iterable.forEach(movimiento -> {
            movimientos.add(builMovimientoResponsePrensenter(movimiento));
            collectionDataSource.add(builMovimientoResponsePrensenter(movimiento));
        });

        HashMap<String, Object> parameters = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        parameters.put("initDate", initDate);
        parameters.put("endDate", endDate);
        parameters.put("cliente", Identificacion);

        byte[] pdf = getReportDataSource("pichincha", parameters, collectionDataSource);
        return Base64.getEncoder().encodeToString(pdf);
    }

    private MovimientoResponsePrensenter builMovimientoResponsePrensenter(Movimiento movimiento) {
        return MovimientoResponsePrensenter.builder().cliente(movimiento.getCuenta().getCliente().getPersona().getNombre())
                .numeroCuenta(movimiento.getCuenta().getNumeroCuenta())
                .tipo(movimiento.getCuenta().getTipoCuenta())
                .fecha(movimiento.getFecha())
                .estado(movimiento.getCuenta().getEstado())
                .saldoDisponible(movimiento.getSaldo())
                .saldoInicial(movimiento.getCuenta().getSaldoInicial())
                .movimiento(movimiento.getValor())
                .movimientoId(movimiento.getMovimientoId()).build();
    }

    private MovimientoPrensenter builMovimientoPrensenter(Movimiento movimiento) {
        return MovimientoPrensenter.builder().movimientoId(movimiento.getMovimientoId())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .fecha(movimiento.getFecha())
                .saldo(movimiento.getSaldo())
                .valor(movimiento.getValor())
                .cuenta(builCuentaPresenter(movimiento.getCuenta()))
                .build();
    }

    private CuentaPresenter builCuentaPresenter(Cuenta cuenta) {
        return CuentaPresenter.builder().cuentaId(cuenta.getCuentaId())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipoCuenta(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldoInicial())
                .estado(cuenta.getEstado())
                .cliente(builClientePresenter(cuenta.getCliente())).build();
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

    private Date[] validateRangeDates(Date initDate, Date endDate) {
        if (initDate != null) {
            initDate = DateUtil.instance().asDate(
                    DateUtil.instance().asLocalDateTime(initDate)
                            .withHour(0).withSecond(0).withMinute(0).withNano(0)
            );
        }
        if (endDate != null) {
            endDate = DateUtil.instance().asDate(
                    DateUtil.instance().asLocalDateTime(endDate).withHour(23).withMinute(59).withSecond(59).withNano(0)
            );
        }
        if ((initDate != null && endDate != null) && initDate.compareTo(endDate) > 0) {
            throw new ValidationException("El rango de fechas es inválido");
        }
        if (initDate != null && endDate == null) {
            endDate = DateUtil.instance().asDate(
                    DateUtil.instance().asLocalDateTime(initDate).withHour(23).withMinute(59).withSecond(59).withNano(0)
            );
        }
        return new Date[]{initDate, endDate};
    }


    private byte[] getReportDataSource(String reportName, HashMap<String, Object> parameters, Collection collectionDataSource) throws JRException, IOException {

        InputStream jasperReport = getCompileReport(reportName);
        JRDataSource dataSource = new JRBeanCollectionDataSource(collectionDataSource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        return exportToPDF(jasperPrint);
    }

    private byte[] exportToPDF(JasperPrint... jasperPrint) throws JRException, IOException {

        byte[] pdf;

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, Arrays.asList(jasperPrint));
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
            exporter.exportReport();
            pdf = byteArrayOutputStream.toByteArray();

        }

        return pdf;
    }

    private InputStream getCompileReport(String reportName) throws IOException {

        ClassPathResource classPathResource = new ClassPathResource("Reports/" + reportName + ".jasper");
        return classPathResource.getInputStream();

    }
}
