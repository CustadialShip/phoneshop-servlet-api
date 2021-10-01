<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="recentlyViewSection" type="com.es.phoneshop.model.product.recentlyview.RecentlyViewSection"
             scope="request"/>
<c:if test="${not empty recentlyViewSection.recentlyView}">
    <div class="block-name">
        Recently viewed
    </div>
    <div class="recently-view-container">
        <c:forEach var="recentlyViewItem" items="${recentlyViewSection.recentlyView}">
            <div class="polaroid">
                <img src="${recentlyViewItem.imageUrl}" alt="Product image" class="mini-image-recently-view">
                <div class="container-polaroid">
                    <p>
                        <a onclick="sendToPDP(${recentlyViewItem.id})">
                                ${recentlyViewItem.description} <br>
                        </a>
                        <a onclick="sendToHistoryPage(${recentlyViewItem.id})">
                            <fmt:formatNumber value="${recentlyViewItem.price}" type="currency"
                                              currencySymbol="${recentlyViewItem.currency.symbol}"/>
                        </a>
                    </p>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>