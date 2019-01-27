package ru.regorov.rrvs.util;

import ru.regorov.rrvs.common.HasId;
import ru.regorov.rrvs.util.exceptions.IllegalRequestDataException;

public class ValidationUtil {

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id){
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }
}
