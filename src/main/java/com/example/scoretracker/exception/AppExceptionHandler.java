package com.example.scoretracker.exception;

import com.example.scoretracker.common.Constant;
import com.example.scoretracker.model.common.ErrorResponse;
import com.example.scoretracker.model.common.ResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus statusCode, WebRequest request) {
        ErrorResponse responseErr = new ErrorResponse();
        if (ex instanceof AppException) {
            responseErr = (ErrorResponse) body;
        } else if (ex instanceof MissingServletRequestParameterException) {
            responseErr.setCode(Constant.STATUS.ERROR);
            responseErr.setMessage("missing parameter");
        } else {
            responseErr.setCode(Constant.STATUS.ERROR);
            responseErr.setMessage(ex.getMessage());
        }

        ResponseDto<?> responseDto = new ResponseDto<>(Constant.STATUS.ERROR, responseErr.getCode(), responseErr.getMessage());
        return super.handleExceptionInternal(ex, responseDto, headers, statusCode, request);
    }


    @ExceptionHandler({AppException.class})
    public ResponseEntity<?> handleAppException(AppException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getErrorResponse(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleAllException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
