package com.api.rest.pichincha.joseromero.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageError {
    private Date timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}