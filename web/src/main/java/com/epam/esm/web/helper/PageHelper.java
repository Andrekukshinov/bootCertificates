package com.epam.esm.web.helper;

import com.epam.esm.persistence.model.page.Pageable;

import java.util.Map;

public interface PageHelper {
    Map<String, String> getPageParamMap(Map<String, String> requestParams, Integer page);

    Pageable getPageable(Map<String, String> requestParams);
}
