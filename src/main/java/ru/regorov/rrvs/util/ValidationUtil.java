package ru.regorov.rrvs.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.regorov.rrvs.common.HasId;
import ru.regorov.rrvs.util.exceptions.ErrorType;
import ru.regorov.rrvs.util.exceptions.IllegalRequestDataException;
import ru.regorov.rrvs.util.exceptions.NotFoundException;
import ru.regorov.rrvs.util.exceptions.RestError;
import ru.regorov.rrvs.web.json.JsonUtil;

import java.util.StringJoiner;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id){
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    private static String getErrorResponseMessage(BindingResult result) {
        StringJoiner joiner = new StringJoiner("; ");
        result.getFieldErrors().forEach(
                fe -> {
                    String msg = fe.getDefaultMessage();
                    if (msg != null) {
                        if (!msg.startsWith(fe.getField())) {
                            msg = fe.getField() + ' ' + msg;
                        }
                        joiner.add(msg);
                    }
                });
        return joiner.toString();
    }

    public static ResponseEntity<String> handleValidationErrors(BindingResult result) {
        String details = ValidationUtil.getErrorResponseMessage(result);
        RestError errModel = new RestError(ErrorType.VALIDATION_ERROR, details);
        return new ResponseEntity<>(JsonUtil.writeValue(errModel), HttpStatus.BAD_REQUEST);
    }
}
