package com.reno.reno.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.reno.reno.model.common.ApiError;
import com.reno.reno.model.exception.ApiException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ApiError> handleMethodArgumentNotValidException(HttpServletRequest req, Exception ex) {
        String statusCode = getStatusCode(HttpStatus.BAD_REQUEST);
        String errorCode = getErrorCode(HttpStatus.BAD_REQUEST);
        Errors errors = ((MethodArgumentNotValidException) ex).getBindingResult();
        List<ApiError> ERRORLIST = new ArrayList<>();
        for (ObjectError item : errors.getAllErrors()) {
            ERRORLIST.add(new ApiError(statusCode, errorCode, item.getDefaultMessage()));
        }
        return ERRORLIST;
    }

    @ExceptionHandler({ ApiException.class, NullPointerException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleApiException(HttpServletRequest req, Exception ex) {
        String statusCode = getStatusCode(HttpStatus.BAD_REQUEST);
        ApiException apiException = new ApiException(statusCode, "System cannot process");
        if (ex instanceof ApiException) {
            apiException = (ApiException) ex;
        }
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        log.error(errors.toString());
        return new ApiError(statusCode, apiException.getErrorCode(), apiException.getPropertyName());
    }

    @ExceptionHandler({ NoSuchElementException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiError handleNoSuchElementException(HttpServletRequest req, Exception ex) {
        int httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ApiException apiException = new ApiException(String.valueOf(httpStatus), "System cannot process");
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        log.error(errors.toString());
        return new ApiError(String.valueOf(httpStatus), apiException.getErrorCode(), apiException.getPropertyName());
    }

    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleHttpRequestMethodNotSupportedException(HttpServletRequest req, Exception ex) {
        String statusCode = getStatusCode(HttpStatus.FORBIDDEN);
        String errorCode = getErrorCode(HttpStatus.FORBIDDEN);
        return new ApiError(statusCode, errorCode, ex.getMessage());
    }

    private String getErrorCode(HttpStatus httpStatus) {
        return String.valueOf(httpStatus.value());
    }

    private String getStatusCode(HttpStatus httpStatus) {
        return String.valueOf(httpStatus.getReasonPhrase());
    }
}
