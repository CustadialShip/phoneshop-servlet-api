package com.es.phoneshop.model.product.advancedSearch;

import com.es.phoneshop.model.product.enums.AdvancedSearchParam;
import com.es.phoneshop.model.product.productdao.ArrayListProductDao;
import com.es.phoneshop.model.product.productdao.Product;
import com.es.phoneshop.model.product.productdao.ProductDao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
    public List<Product> search(List<String> searchTextList, BigDecimal minPrice, BigDecimal maxPrice, AdvancedSearchParam param) {
        lock.readLock().lock();
        if(minPrice == null){
            minPrice = new BigDecimal(0);
        }
        if(maxPrice == null){
            maxPrice = new BigDecimal(Integer.MAX_VALUE);
        }
        List<Product> products = productDao.findProducts(null, null, null);
        try {

                BigDecimal finalMinPrice = minPrice;
                BigDecimal finalMaxPrice = maxPrice;
                products = products.stream()
                        .filter(product -> shouldDisplayProduct(product, searchTextList, param))
                        .filter(product -> product.getPrice().compareTo(finalMinPrice) > 0 && product.getPrice().compareTo(finalMaxPrice) < 0)
                        .collect(toList());
            return products;
        } finally {
            lock.readLock().unlock();
        }
    }

    private boolean shouldDisplayProduct(Product product, List<String> searchTextList, AdvancedSearchParam param) {
        if((searchTextList != null && !searchTextList.isEmpty()) &&
                !(searchTextList.size() == 1 && searchTextList.get(0).equals(""))){
            return true;
        }
        List<String> searchTextLowerCase = searchTextList.stream()
                .map(String::toLowerCase)
                .collect(toList());
        List<String> descriptionList = Arrays.asList(product.getDescription()
                .toLowerCase(Locale.ROOT).split("\\s"));
        if(param == AdvancedSearchParam.ANY){
//            searchTextLowerCase.retainAll(descriptionList);
        } else {
            searchTextLowerCase.retainAll(descriptionList);
        }
        return searchTextList.isEmpty() || (searchTextLowerCase.size() != 0);
    }

    @Override
    public List<AdvancedSearchParam> getSearchParam() {
        return Arrays.asList(AdvancedSearchParam.values());
    }
}