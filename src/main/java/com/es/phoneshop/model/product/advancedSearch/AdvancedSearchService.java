package com.es.phoneshop.model.product.advancedSearch;

import com.es.phoneshop.model.product.enums.AdvancedSearchParam;
import com.es.phoneshop.model.product.productdao.Product;

import java.math.BigDecimal;
import java.util.List;

public interface AdvancedSearchService {
    List<Product> search(List<String> searchTextList, BigDecimal minPrice, BigDecimal maxPrice, AdvancedSearchParam param);

    List<AdvancedSearchParam> getSearchParam();
}