package com.epam.esm.web.helper;

import com.epam.esm.persistence.model.page.Pageable;
import com.epam.esm.web.exception.InvalidValueException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PageHelperImpl implements PageHelper {
    private static final int DEFAULT_SIZE = 7;
    private static final int DEFAULT_PAGE = 0;

    public Map<String, String> getPageParamMap(Map<String, String> requestParams, Integer page) {
        HashMap<String, String> result = new HashMap<>(requestParams);
        result.put("page", page.toString());
        return result;
    }

    public Map<String, String> getThisPageParamMap(Map<String, String> requestParams, Integer currentPage) {
        return getPageParamMap(requestParams, currentPage + 1);
    }

    public Map<String, String> getNextPageParamMap(Map<String, String> requestParams, Integer nextPage) {
        return getThisPageParamMap(requestParams, nextPage);
    }

    public Map<String, String> getPreviousPageParamMap(Map<String, String> requestParams, Integer previousPage) {
        return getThisPageParamMap(requestParams, previousPage);
    }

    public Pageable getPageable(Map<String, String> requestParams) {
        Integer page = getPage(requestParams);
        Integer size = getSize(requestParams);
        String sort = requestParams.get("sort");
        String sortDir = requestParams.get("sortDir");
        return new Pageable(page, size, sort, sortDir);
    }

    private Integer getSize(Map<String, String> requestParams) {
        String sizeString = requestParams.get("size");
        if (sizeString == null) {
            return DEFAULT_SIZE;
        } else if (sizeString.matches("[0-9]+")) {
            return Integer.valueOf(sizeString);
        } else {
            throw new InvalidValueException("size must be a positive integer!");
        }
    }

    private Integer getPage(Map<String, String> requestParams) {
        String sizeString = requestParams.get("page");
        if (sizeString == null) {
            return DEFAULT_PAGE;
        } else if (sizeString.matches("[1-9][0-9]*")) {
            return Integer.parseInt(sizeString) - 1;
        } else {
            throw new InvalidValueException("page must be a positive integer!");
        }
    }

}
