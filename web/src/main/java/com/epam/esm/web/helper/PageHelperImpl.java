package com.epam.esm.web.helper;

import com.epam.esm.persistence.model.page.Pageable;
import com.epam.esm.web.exception.InvalidSizeException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PageHelperImpl implements PageHelper{
    private static final int DEFAULT_SIZE = 7;

    public Map<String, String> getPageParamMap(Map<String, String> requestParams, Integer page) {
        HashMap<String, String> result = new HashMap<>(requestParams);
        result.put("page", page.toString());
        return result;
    }

    public Pageable getPageable(Map<String, String> requestParams) {
        Integer page = Integer.valueOf(requestParams.get("page"));
        Integer size = getSize(requestParams);
        String sort = requestParams.get("sort");
        String sortDir = requestParams.get("sortDir");
        return new Pageable(page, size, sort, sortDir);
    }

    private Integer getSize(Map<String, String> requestParams) {
        String sizeString = requestParams.get("size");
        if (sizeString == null){
            return DEFAULT_SIZE;
        } else if (sizeString.matches("[0-9]+")) {
            return Integer.valueOf(sizeString);
        } else {
            throw new InvalidSizeException("size must be a positive integer!");
        }
    }

}
