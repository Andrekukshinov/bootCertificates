package com.epam.esm.persistence.model.page;

import java.io.Serializable;

public class Pageable implements Serializable {
    private boolean isPaged = true;
    private Integer page;
    private Integer size;
    private String sort;
    private String sortDir;

    public Pageable() {
        isPaged = false;
    }

    public static Pageable unpaged() {
        return new Pageable();
    }

    public Pageable(Integer page, Integer size, String sort, String sortDir) {
        this.page = page;
        this.size = size;
        this.sort = sort;
        this.sortDir = sortDir;
    }

    public boolean isPaged() {
        return isPaged;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public String getSort() {
        return sort;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setPaged(boolean paged) {
        isPaged = paged;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }
}
