package com.es.phoneshop.web;

import com.es.phoneshop.model.product.productdao.ArrayListProductDao;
import com.es.phoneshop.model.product.productdao.PriceHistory;
import com.es.phoneshop.model.product.productdao.Product;
import com.es.phoneshop.model.product.productdao.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductPriceHistoryPageServlet extends HttpServlet {

    private static final String PRODUCT_HISTORY_LIST = "productHistoryList";
    public static final String PRODUCT_PRICE_HISTORY_PAGE_JSP = "/WEB-INF/pages/productPriceHistory.jsp";

    private ProductDao productDao;

    @Override
    public void init() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getPathInfo();
        Product product = productDao.getProduct(Long.valueOf(productId.substring(1)));
        request.setAttribute("product", product);
        List<PriceHistory> priceHistoryList = product.getPriceHistoryList();
        if (priceHistoryList == null || priceHistoryList.isEmpty()) {
            request.setAttribute(PRODUCT_HISTORY_LIST, new ArrayList<>());
        } else {
            request.setAttribute(PRODUCT_HISTORY_LIST, priceHistoryList);
        }
        request.getRequestDispatcher(PRODUCT_PRICE_HISTORY_PAGE_JSP).forward(request, response);
    }
}
