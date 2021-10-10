package com.es.phoneshop.model.product.advancedSearch;

import com.es.phoneshop.model.product.enums.AdvancedSearchParam;
import com.es.phoneshop.model.product.exceptions.MinMaxPriceException;
import com.es.phoneshop.model.product.productdao.ArrayListProductDao;
import com.es.phoneshop.model.product.productdao.Product;
import com.es.phoneshop.model.product.productdao.ProductDao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class DefaultAdvancedSearchService implements AdvancedSearchService {
    private static volatile DefaultAdvancedSearchService instance;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private ProductDao productDao;

    private DefaultAdvancedSearchService() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static DefaultAdvancedSearchService getInstance() {
        DefaultAdvancedSearchService cart = instance;
        if (cart != null) {
            return cart;
        }
        synchronized (DefaultAdvancedSearchService.class) {
            if (instance == null) {
                instance = new DefaultAdvancedSearchService();
            }
            return instance;
        }
    }

    @Override
    public List<Product> search(List<String> searchTextList, BigDecimal minPrice, BigDecimal maxPrice, AdvancedSearchParam param) throws MinMaxPriceException {
        lock.readLock().lock();
        try {
            if (minPrice == null || maxPrice == null) {
                throw new MinMaxPriceException("price should be integer");
            }
            if (minPrice.compareTo(maxPrice) > 0) {
                throw new MinMaxPriceException("minPrice should be < maxPrice");
            }
            List<Product> products = productDao.findProducts(null, null, null);
            return products.stream()
                    .filter(item -> item.getPrice().compareTo(minPrice) >= 0 && item.getPrice().compareTo(maxPrice) <= 0)
                    .filter(item -> isSuitableForSearch(item, param, searchTextList))
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock();
        }
    }

    private boolean isSuitableForSearch(Product product, AdvancedSearchParam param, List<String> searchTextList) {
        if (searchTextList == null || searchTextList.isEmpty() ||
                (searchTextList.size() == 1 && searchTextList.get(0).equals(""))) {
            return true;
        }
        List<String> searchTextListLowerCase = searchTextList.stream()
                .map(String::toLowerCase)
                .collect(toList());
        List<String> descriptionList = Arrays.asList(product.getDescription()
                .toLowerCase(Locale.ROOT).split("\\s"));
        int searchTextListSize = descriptionList.size();
        searchTextListLowerCase.retainAll(descriptionList);
        if (param == AdvancedSearchParam.ALL && searchTextListLowerCase.size() == searchTextListSize) {
            return true;
        } else return param == AdvancedSearchParam.ANY && searchTextListLowerCase.size() > 0;
    }

    @Override
    public List<AdvancedSearchParam> getSearchParam() {
        return Arrays.asList(AdvancedSearchParam.values());
    }
}