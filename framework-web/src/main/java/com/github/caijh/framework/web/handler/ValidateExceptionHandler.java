package com.github.caijh.framework.web.handler;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.github.caijh.framework.core.model.R;
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

@RestControllerAdvice(annotations = Controller.class)
public class ValidateExceptionHandler {

    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseEntity<R<Void>> validExceptionHandler(Exception e) {
        BindingResult bindingResult = null;
        if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        } else if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        R<Void> result = new R<>();
        if (bindingResult != null) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            JSONObject errMsg = new JSONObject();
            for (FieldError error : fieldErrors) {
                errMsg.put(error.getField(), error.getDefaultMessage());
            }
            result.setMessage(errMsg.toString());
        } else {
            result.setMessage(e.getLocalizedMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
