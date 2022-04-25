package com.api.rest.pichincha.joseromero.exception.handler;



import com.api.rest.pichincha.joseromero.exception.ExceptionResponse;
import com.api.rest.pichincha.joseromero.exception.MegaPosException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GenericExceptionHandler {

    public static final String RESPONSE_STATUS_KEY = "responseStatusKey";
    public static final String REQUEST_ID = "requestId";


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> genericExceptionHandler(Exception exception) {
        HttpStatus internalServerError;
        if(exception instanceof AccessDeniedException){
            internalServerError = HttpStatus.FORBIDDEN;
        }else{
            internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message("Ocurrió un error inesperado")
                .build();

        ResponseEntity<ExceptionResponse> response = new ResponseEntity<>(
                exceptionResponse,
                internalServerError
        );

        return response;
    }

    @ExceptionHandler(MegaPosException.class)
    public ResponseEntity<ExceptionResponse> customExceptionHandler(MegaPosException exception) {
        ResponseEntity<ExceptionResponse> response = new ResponseEntity<>(exception.getExceptionResponse(), exception.getHttpStatus());


        return response;
    }

}
