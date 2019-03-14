package ru.regorov.rrvs.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.regorov.rrvs.util.ValidationUtil;
import ru.regorov.rrvs.util.exceptions.*;

@RestControllerAdvice
public class RestExceptionHandler {
    private static Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);
    private static final String USER_UNIQUE_LOGIN = "user_unique_login_idx";
    private static final String USER_ROLE_UNIQUE = "user_roles_idx";
    private static final String RESTAURANT_UNIQUE_PHONE = "restaurant_unique_phone_idx";
    private static final String MENU_DISH_UNIQUE = "menu_dish_idx";
    private static final String NONEXISTENT_FK = "foreign key no parent";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public RestError handleNotFound(NotFoundException e) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        log.error(e.getLocalizedMessage(), rootCause);
        return new RestError(ErrorType.DATA_NOT_FOUND_ERROR, ValidationUtil.getMessage(rootCause));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EndVoteException.class)
    public RestError handleEndVoting(EndVoteException e) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        log.error(e.getLocalizedMessage(), rootCause);
        return new RestError(ErrorType.END_VOTE_ERROR, ValidationUtil.getMessage(rootCause));
    }

    //TODO заменить на реализацию через валидатор с запросом сущности в базе,
    //что бы запрос с неуникальным полем не приводил к ошибке БД
    //плюс внешние ключи (например голосуем за несуществующий ресторан)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public RestError handleDataIntergityViolation(DataIntegrityViolationException e) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        log.error(e.getLocalizedMessage(), rootCause);
        String message = ValidationUtil.getMessage(rootCause).toLowerCase();
        if (message.contains(USER_UNIQUE_LOGIN)) {
            message = "Пользователь с таким полем 'login' уже существует";
        } else if (message.contains(USER_ROLE_UNIQUE)) {
            message = "Пользователь с такой ролью уже существует";
        } else if (message.contains(RESTAURANT_UNIQUE_PHONE)) {
            message = "Ресторан с таким полем 'phone' уже существует";
        } else if (message.contains(MENU_DISH_UNIQUE)) {
            message = "В этом меню такое блюдо уже добавлено";
        } else if (message.contains(NONEXISTENT_FK)) {
            message = "Объекта с таким идентификатором не существует";
        }
        return new RestError(ErrorType.DATA_ERROR, message);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public RestError handleIllegalRequestData(Exception e) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        log.error(e.getLocalizedMessage(), rootCause);
        return new RestError(ErrorType.VALIDATION_ERROR, ValidationUtil.getMessage(rootCause));
    }

    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RestError handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        log.error(e.getLocalizedMessage(), rootCause);
        return new RestError(ErrorType.METHOD_NOT_ALLOWED, ValidationUtil.getMessage(rootCause));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public RestError handleCommonException(Exception e) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        log.error(e.getLocalizedMessage(), rootCause);
        return new RestError(ErrorType.APP_ERROR, ValidationUtil.getMessage(rootCause));
    }

    //TODO добавить в контроллеры валидацию @valid и constrains в dto, что бы работала аннотация
}
