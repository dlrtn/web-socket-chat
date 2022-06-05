package com.dlrtn.websocket.chat.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ValidationException;
import java.net.BindException;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<CommonError> handle(Throwable cause) {
        CommonException exception = new CommonException(cause.getMessage(), cause);
        return handleCommonException(exception);
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<CommonError> handle(HttpStatusCodeException cause) {
        CommonException exception = new CommonException(cause.getMessage(), cause, cause.getStatusCode());
        return handleCommonException(exception);
    }

    @ExceptionHandler({
            ServletRequestBindingException.class,
            ValidationException.class,
            BindException.class,
            TypeMismatchException.class,
            IllegalArgumentException.class
    })

    public ResponseEntity<CommonError> handleBadRequest(Exception cause) {
        CommonException exception = new CommonException(cause.getMessage(), cause, HttpStatus.BAD_REQUEST);
        return handleCommonException(exception);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<CommonError> handleForbidden(Exception cause) {
        CommonException exception = new CommonException(cause.getMessage(), cause, HttpStatus.FORBIDDEN);
        return handleCommonException(exception);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<CommonError> handleNotFound(Exception cause) {
        CommonException exception = new CommonException(cause.getMessage(), cause, HttpStatus.NOT_FOUND);
        return handleCommonException(exception);
    }

    @ExceptionHandler({HttpMediaTypeException.class})
    public ResponseEntity<CommonError> handleUnsupportedMediaType(Exception cause) {
        CommonException exception = new CommonException(cause.getMessage(), cause, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        return handleCommonException(exception);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonError> handleCommonException(CommonException exception) {
        CommonError error = CommonError.builder()
                .code(exception.getHttpStatus().value())
                .error(exception.getHttpStatus().getReasonPhrase())
                .message(exception.getMessage())
                .build();

        try {
            HttpStatus httpStatus = exception.getHttpStatus();

            if (httpStatus.is4xxClientError()) {
                if (httpStatus != HttpStatus.NOT_FOUND) {
                    String message = Optional.ofNullable(exception.getCause())
                            .orElse(exception)
                            .getMessage();
                    log.warn("status code: {}, message: {}", httpStatus, message);
                }
            } else {
                log.error("status code: {}, message: {}", httpStatus, exception.getMessage(), exception);
            }

            return ResponseEntity.status(httpStatus).body(error);
        } catch (Exception e) {
            log.error("status code: {}", exception.getHttpStatus(), e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
        }
    }

}
