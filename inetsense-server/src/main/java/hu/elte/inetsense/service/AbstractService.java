package hu.elte.inetsense.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Contains common service methods and fields.
 *
 * @author Zsolt Istvanfi
 */
public abstract class AbstractService {

    @Autowired
    protected Mapper dozer;

    protected <T> T dozer(final Object source, final Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    protected <T> List<T> dozerAll(final Collection<?> data, final Class<T> targetClass) {
        if (data == null || data.isEmpty() || targetClass == null) {
            return Collections.emptyList();
        }

        List<T> result = new ArrayList<>(data.size());

        for (Object source : data) {
            result.add(dozer.map(source, targetClass));
        }

        return result;
    }

}
