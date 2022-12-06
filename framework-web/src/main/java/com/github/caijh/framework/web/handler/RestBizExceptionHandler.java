package com.github.caijh.framework.web.handler;


import com.github.caijh.framework.core.exceptions.BizException;
import com.github.caijh.framework.core.model.request.Result;
import jakarta.inject.Inject;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 统一处理业务异常，返回国际化信息.
 */
@Order(1)
@RestControllerAdvice(annotations = RestController.class)
public class RestBizExceptionHandler extends ResponseEntityExceptionHandler {

    @Inject
    private MessageSource messageSource;

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ResponseEntity<Result<Void>> handleBizException(BizException exception) {
        Result<Void> result = new Result<>();
        String code = exception.getCode();
        if (code != null) {
            String message = this.messageSource.getMessage(code, exception.getParams(), LocaleContextHolder.getLocale());
            result.setCode(code).setMessage(message);
        } else {
            result.setMessage(exception.getLocalizedMessage());
        }
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
