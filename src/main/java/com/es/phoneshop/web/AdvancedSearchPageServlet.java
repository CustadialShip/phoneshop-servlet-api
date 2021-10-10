package com.es.phoneshop.web;

import com.es.phoneshop.model.product.advancedSearch.AdvancedSearchService;
import com.es.phoneshop.model.product.advancedSearch.DefaultAdvancedSearchService;
import com.es.phoneshop.model.product.enums.AdvancedSearchParam;
import com.es.phoneshop.model.product.exceptions.LowerZeroException;
import com.es.phoneshop.model.product.exceptions.MinMaxPriceException;
import com.es.phoneshop.model.product.productdao.Product;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class AdvancedSearchPageServlet extends HttpServlet {

    public static final String ADVANCED_DESCRIPTION = "advancedDescription";
    public static final String MIN_PRICE = "minPrice";
    public static final String MAX_PRICE = "maxPrice";
    public static final String ADVANCED_SEARCH_PARAM_LIST = "advancedSearchParamList";
    public static final String ADVANCED_SEARCH_PAGE_JSP = "/WEB-INF/pages/advancedSearchPage.jsp";
    public static final String ADVANCED_SEARCH_PARAM = "advancedSearchParam";

    private AdvancedSearchService advancedSearchService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        advancedSearchService = DefaultAdvancedSearchService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ADVANCED_SEARCH_PARAM_LIST, advancedSearchService.getSearchParam());
        AdvancedSearchParam advancedSearchParam = parseAdvanceSearchParam(request);
        List<String> advancedDescriptionList = parseSearchText(request);
        Map<String, String> errorsMap = new HashMap<>();
        Integer minPriceValue = getPriceParam(errorsMap, request, MIN_PRICE);
        Integer maxPriceValue = getPriceParam(errorsMap, request, MAX_PRICE);
        List<Product> result = new ArrayList<>();
        if (errorsMap.isEmpty()) {
            try {
                BigDecimal minPrice = minPriceValue != null ? new BigDecimal(minPriceValue) : null;
                BigDecimal maxPrice = maxPriceValue != null ? new BigDecimal(maxPriceValue) : null;
                result = advancedSearchService.search(advancedDescriptionList, minPrice, maxPrice, advancedSearchParam);
            } catch (MinMaxPriceException exception) {
                errorsMap.put("minMaxPrice", exception.getMessage());
            }
        }
        request.setAttribute(ADVANCED_SEARCH_PARAM, advancedSearchParam);
        request.setAttribute("errors", errorsMap);
        request.setAttribute("productList", result);
        request.getRequestDispatcher(ADVANCED_SEARCH_PAGE_JSP).forward(request, response);
    }

    private AdvancedSearchParam parseAdvanceSearchParam(HttpServletRequest request) {
        String advancedParamString = request.getParameter("advancedParam");
        return advancedParamString == null ? AdvancedSearchParam.ALL : AdvancedSearchParam.valueOf(advancedParamString);
    }

    private Integer getPriceParam(Map<String, String> errorMap, HttpServletRequest request, String paramName) {
        String priceString = request.getParameter(paramName);
        if (Objects.equals(paramName, MIN_PRICE) && (priceString == null || priceString.isEmpty())) {
            return Integer.MIN_VALUE;
        } else if (Objects.equals(paramName, MAX_PRICE) && (priceString == null || priceString.isEmpty())) {
            return Integer.MAX_VALUE;
        } else {
            try {
                int priceValue = Integer.parseInt(priceString);
                if (priceValue < 0) {
                    throw new LowerZeroException("price should be >= 0");
                }
                return priceValue;
            } catch (NumberFormatException exception) {
                errorMap.put(paramName, "Price should be integer");
            } catch (LowerZeroException exception) {
                errorMap.put(paramName, exception.getMessage());
            }
            return null;
        }
    }

    private List<String> parseSearchText(HttpServletRequest request) {
        String searchText = request.getParameter(ADVANCED_DESCRIPTION);
        if (searchText == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(request.getParameter(ADVANCED_DESCRIPTION).split("\\s"));
    }
}