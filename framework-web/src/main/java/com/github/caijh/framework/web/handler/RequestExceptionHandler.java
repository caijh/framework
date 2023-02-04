package com.github.caijh.framework.web.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.ValidationException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.caijh.framework.core.model.request.Result;
import jakarta.inject.Inject;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 * 拦截controller中的异常
 */
@RestControllerAdvice(annotations = Controller.class)
@Order(1)
public class RequestExceptionHandler {

    private final ObjectMapper mapper;

    @Inject
    private MessageSource messageSource;

    public RequestExceptionHandler() {
        this.mapper = new ObjectMapper();
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class, ValidationException.class})
    @ResponseBody
    public ResponseEntity<Result<Void>> handle(Exception e) {
        BindingResult bindingResult = null;
        if (e instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            bindingResult = methodArgumentNotValidException.getBindingResult();
        } else if (e instanceof BindException bindException) {
            bindingResult = bindException.getBindingResult();
        }
        Result<Void> result = new Result<>();
        if (bindingResult != null) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            Map<String, String> errMsg = new HashMap<>();
            for (FieldError error : fieldErrors) {
                errMsg.put(error.getField(), messageSource.getMessage(error, LocaleContextHolder.getLocale()));
            }
            try {
                result.setMessage(this.mapper.writeValueAsString(errMsg));
            } catch (JsonProcessingException jsonProcessingException) {
                result.setMessage("ValidExceptionHandler write field error message fail");
            }
        } else {
            result.setMessage(e.getLocalizedMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

}
