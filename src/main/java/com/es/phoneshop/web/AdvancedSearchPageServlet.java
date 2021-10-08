package com.es.phoneshop.web;

import com.es.phoneshop.model.product.advancedSearch.AdvancedSearchService;
import com.es.phoneshop.model.product.advancedSearch.DefaultAdvancedSearchService;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.DefaultCartService;
import com.es.phoneshop.model.product.enums.AdvancedSearchParam;
import com.es.phoneshop.model.product.exceptions.ZeroException;
import com.es.phoneshop.model.product.productdao.ArrayListProductDao;
import com.es.phoneshop.model.product.productdao.Product;
import com.es.phoneshop.model.product.productdao.ProductDao;

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
    public static final String ADVANCED_SEARCH_PARAM = "advancedSearchParamList";
    public static final String ADVANCED_SEARCH_PAGE_JSP = "/WEB-INF/pages/advancedSearchPage.jsp";

    private ProductDao productDao;
    private CartService cartService;
    private WebHelperService webHelperService;
    private AdvancedSearchService advancedSearchService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cartService = DefaultCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
        webHelperService = WebHelperService.getInstance();
        advancedSearchService = DefaultAdvancedSearchService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ADVANCED_SEARCH_PARAM, advancedSearchService.getSearchParam());
        String minPriceString = request.getParameter(MIN_PRICE);
        String maxPriceString = request.getParameter(MAX_PRICE);
        String advancedParamString = request.getParameter("advancedParam");
        AdvancedSearchParam advancedParam = advancedParamString == null ? AdvancedSearchParam.ALL : AdvancedSearchParam.valueOf(advancedParamString);
        List<String> advancedDescriptionList = parseSearchText(request);
        Map<String, String> errorsMap = new HashMap<>();

        try {
            int minPriceValue = Integer.parseInt(minPriceString);
            if (minPriceValue < 0) {
                throw new ZeroException("minPrice < 0");
            }
        } catch (NumberFormatException exception) {
            setErrorMessageToMap("minPrice", errorsMap, "minPrice should be integer");
        } catch (ZeroException exception) {
            setErrorMessageToMap("minPrice", errorsMap, "minPrice < 0");
        }
        try {
            int maxPriceValue = Integer.parseInt(maxPriceString);
            if (maxPriceValue < 0) {
                throw new ZeroException("maxPrice < 0");
            }
        } catch (NumberFormatException exception) {
            setErrorMessageToMap("maxPrice", errorsMap, "maxPrice should be integer");
        } catch (ZeroException exception) {
            setErrorMessageToMap("maxPrice", errorsMap, "maxPrice < 0");
        }
        List<Product> result = new ArrayList<>();
        if(errorsMap.isEmpty()){
            result = advancedSearchService.search(advancedDescriptionList,
                    new BigDecimal(Integer.parseInt(minPriceString)),
                    new BigDecimal(Integer.parseInt(maxPriceString)),
                    advancedParam
            );
        }
        request.setAttribute("productList", result);
        request.getRequestDispatcher(ADVANCED_SEARCH_PAGE_JSP).forward(request, response);
    }

    private List<String> parseSearchText(HttpServletRequest request) {
        String searchText = request.getParameter(ADVANCED_DESCRIPTION);
        if(searchText == null){
            return new ArrayList<>();
        }
        return Arrays.asList(request.getParameter(ADVANCED_DESCRIPTION).split("\\s"));
    }

    private void setErrorMessageToMap(String parameterName, Map<String, String> errorsMap, String errorMessage) {
        errorsMap.put(parameterName, errorMessage);
    }
}