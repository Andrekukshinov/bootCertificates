package com.epam.esm.persistence.model.page;

import java.util.List;

public class PageImpl<T> implements Page<T> {
    private final List<T> content;
    private final Integer lastPage;
    private final Integer firstPage;
    private final Integer page;
    private final Integer nextPage;
    private final Integer previousPage;

    public PageImpl(List<T> content, Pageable pageable, Integer lastPage) {
        this.content = content;
        this.page = pageable.getPage();
        this.lastPage = lastPage;
        this.firstPage = 1;
        this.nextPage = page + 1;
        this.previousPage = page - 1;
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public Integer getPage() {
        return page;
    }

    @Override
    public Integer getLastPage() {
        return lastPage;
    }

    @Override
    public Integer getFirstPage() {
        return firstPage;
    }

    @Override
    public Integer getNextPage() {
        return nextPage;
    }

    @Override
    public Integer getPreviousPage() {
        return previousPage;
    }

    @Override
    public boolean hasPrevious() {
        return page > 1 && page < (lastPage - 1);
    }

    @Override
    public boolean hasNext() {
        return page < lastPage && page > 0;
    }
}
