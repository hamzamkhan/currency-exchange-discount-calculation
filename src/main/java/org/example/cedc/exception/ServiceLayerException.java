package org.example.cedc.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@Getter
@Setter
@AllArgsConstructor
public class ServiceLayerException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private final HttpStatus httpStatus;

    public ServiceLayerException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
