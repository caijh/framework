package com.github.caijh.framework.web.handler;

import com.github.caijh.framework.core.exceptions.LocalizedException;
import com.github.caijh.framework.core.model.request.Result;
import jakarta.inject.Inject;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 统一处理业务异常，返回国际化信息.
 */
@Order(1)
@RestControllerAdvice(annotations = RestController.class)
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Inject
    private MessageSource messageSource;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> handleBizException(Exception exception) {
        Result<Void> result = new Result<>();
        if (exception instanceof LocalizedException localizedException) {
            String code = localizedException.getCode();
            String message = this.messageSource.getMessage(code, localizedException.getParams(), code, LocaleContextHolder.getLocale());
            result.setCode(code).setMessage(message);
        } else {
            result.setMessage(exception.getLocalizedMessage());
        }
        return result;
    }

}