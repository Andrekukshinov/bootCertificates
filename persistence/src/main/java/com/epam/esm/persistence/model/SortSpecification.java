package com.epam.esm.persistence.model;

import com.epam.esm.persistence.model.enums.SortDirection;

public class SortSpecification {
    private SortDirection createDateSortDir;
    private SortDirection nameSortDir;

    public SortSpecification(SortDirection createDateSortDir, SortDirection nameSortDir) {
        this.createDateSortDir = createDateSortDir;
        this.nameSortDir = nameSortDir;
    }

    public SortSpecification() {
    }

    public SortDirection getCreateDateSortDir() {
        return createDateSortDir;
    }

    public void setCreateDateSortDir(SortDirection createDateSortDir) {
        this.createDateSortDir = createDateSortDir;
    }

    public SortDirection getNameSortDir() {
        return nameSortDir;
    }

    public void setNameSortDir(SortDirection nameSortDir) {
        this.nameSortDir = nameSortDir;
    }

    @Override
    public String toString() {
        return "SortSpecification{" +
                "createDateSortDir=" + createDateSortDir +
                ", nameSortDir=" + nameSortDir +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SortSpecification that = (SortSpecification) o;

        if (getCreateDateSortDir() != that.getCreateDateSortDir()) {
            return false;
        }
        return getNameSortDir() == that.getNameSortDir();
    }

    @Override
    public int hashCode() {
        int result = getCreateDateSortDir() != null ? getCreateDateSortDir().hashCode() : 0;
        result = 31 * result + (getNameSortDir() != null ? getNameSortDir().hashCode() : 0);
        return result;
    }
}
