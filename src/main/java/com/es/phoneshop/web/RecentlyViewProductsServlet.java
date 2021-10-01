package com.es.phoneshop.web;

import com.es.phoneshop.model.product.recentlyview.DefaultRecentlyViewService;
import com.es.phoneshop.model.product.recentlyview.RecentlyViewService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RecentlyViewProductsServlet extends HttpServlet {

    public static final String RECENTLY_VIEW_PRODUCTS_PAGE_JSP = "/WEB-INF/pages/recentlyViewProducts.jsp";
    public static final String RECENTLY_VIEW_SECTION = "recentlyViewSection";
    private
    RecentlyViewService recentlyViewService;

    @Override
    public void init() {
        recentlyViewService = DefaultRecentlyViewService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(RECENTLY_VIEW_SECTION, recentlyViewService.getRecentlyViewSection(request));
        request.getRequestDispatcher(RECENTLY_VIEW_PRODUCTS_PAGE_JSP).include(request, response);
    }
}