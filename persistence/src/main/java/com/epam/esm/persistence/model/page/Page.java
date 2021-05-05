package com.epam.esm.persistence.model.page;

import java.util.List;

public interface Page<T> {
    List<T> getContent();

    Integer getFirstPage();

    Integer getLastPage();

    Integer getPage();

    boolean hasNext();

    boolean hasPrevious();

    boolean hasFirst();

    boolean hasLast();

    Integer getNextPage();

    Integer getPreviousPage();
}
