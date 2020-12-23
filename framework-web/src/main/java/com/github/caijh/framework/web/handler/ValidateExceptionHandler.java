package com.github.caijh.framework.web.handler;

import java.util.List;

import com.github.caijh.framework.core.model.R;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(annotations = Controller.class)
public class ValidateExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<R<Void>> validExceptionHandler(Exception e) {
        BindingResult bindingResult = null;
        if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        } else if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        if (bindingResult != null) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            JsonObject errMsg = new JsonObject();
            for (FieldError error : fieldErrors) {
                errMsg.addProperty(error.getField(), error.getDefaultMessage());
            }
            R<Void> result = new R<>();
            result.setMessage(errMsg.toString());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return null;
    }

}
