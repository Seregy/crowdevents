package com.crowdevents.core.web;

import org.springframework.data.domain.Sort;

import java.util.Optional;

public interface CursorPagable<ID> {
    /**
     * Returns the number of items to be returned.
     *
     * @return the number of items of that page
     */
    int getPageSize();

    /**
     * Returns the id after which items should start.
     *
     * @return
     */
    Optional<ID> getStartingAfterId();

    /**
     * Returns the id before which items should start.
     *
     * @return
     */
    Optional<ID> getEndingBeforeId();

    /**
     * Returns the sorting parameters.
     *
     * @return
     */
    Sort getSort();

    /**
     * Returns the {@link CursorPagable} requesting the next {@link CursorPage}.
     *
     * @return
     */
    CursorPagable next();

    /**
     * Returns the previous {@link CursorPagable} or the first {@link CursorPagable} if the current one already is the first one.
     *
     * @return
     */
    CursorPagable previousOrFirst();


    /**
     * Returns whether there's a previous {@link CursorPagable} we can access from the current one. Will return
     * {@literal false} in case the current {@link CursorPagable} already refers to the first page.
     *
     * @return
     */
    boolean hasPrevious();
}
