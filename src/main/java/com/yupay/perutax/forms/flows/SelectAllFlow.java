package com.yupay.perutax.forms.flows;

import com.yupay.perutax.dao.DAO;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

/**
 * Data flow to run a SELECT * FROM T query.
 *
 * @author InfoYupay SACS
 * @version 1.0
 */
public final class SelectAllFlow<T> extends DataFlowBase<T> {
    /**
     * Default constructor.
     *
     * @param entity the entity class.
     */
    public SelectAllFlow(Class<T> entity) {
        super(entity);
    }

    @Override
    protected @NotNull Stream<T> getQuery() {
        return DAO.forEntity(entity).findAll();
    }
}
