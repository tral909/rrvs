package ru.regorov.rrvs.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.regorov.rrvs.util.ValidationUtil;
import ru.regorov.rrvs.util.exceptions.EndVoteException;
import ru.regorov.rrvs.util.exceptions.ErrorType;
import ru.regorov.rrvs.util.exceptions.NotFoundException;
import ru.regorov.rrvs.util.exceptions.RestError;

@RestControllerAdvice
public class RestExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public RestError handleNotFound(NotFoundException e) {
        return new RestError(ErrorType.DATA_NOT_FOUND_ERROR, ValidationUtil.getMessage(e));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EndVoteException.class)
    public RestError handleEndVoting(EndVoteException e) {
        return new RestError(ErrorType.END_VOTE_ERROR, ValidationUtil.getMessage(e));
    }

    //TODO доделать здесь все unique ограничения из базы
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public RestError handleDataIntergityViolation(DataIntegrityViolationException e) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        String message = ValidationUtil.getMessage(rootCause).toLowerCase();
        if (message.contains("user_unique_login_idx")) {
            message = "Пользователь с таким полем 'login' уже существует";
        } else if (message.contains("user_roles_idx")) {
            message = "Пользователь с такой ролью уже существует";
        }
        return new RestError(ErrorType.DATA_ERROR, message);
    }

    //TODO закончить с остальными исключениями
}
