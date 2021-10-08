<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="advancedSearchParamList" type="java.util.List" scope="request"/>
<jsp:useBean id="productList" type="java.util.List" scope="request"/>

<tags:master pageTitle="Advanced search">
    <script>
        function sendToHistoryPage(id) {
            window.open("${pageContext.servletContext.contextPath}/priceHistory/" + id,
                "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
        }

        function sendToPDP(id) {
            window.open("${pageContext.servletContext.contextPath}/products/" + id,
                "_self", "toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=500,width=400,height=400");
        }
    </script>
    <p>
    <div class="main-name">Advanced search</div>
    </p>
    <form action="${pageContext.servletContext.contextPath}/advancedSearch">
        <table class="light-green" style="width: 25%">
            <tr>
                <td>Description:</td>
                <td><input type="text" name="advancedDescription">
                    <select name="advanceParam">
                        <c:forEach var="advancedSearchParam" items="${advancedSearchParamList}">
                            <c:choose>
                                <c:when test="${advancedSearchParam eq order.paymentMethod}">
                                    <option value="${advancedSearchParam}"
                                            selected="selected">${advancedSearchParam}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${advancedSearchParam}">${advancedSearchParam}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Min price:</td>
                <td><input type="text" name="minPrice">
                </td>
            </tr>
            <tr>
                <td>Max price:</td>
                <td><input type="text" name="maxPrice"></td>
            </tr>
        </table>
        <button>Search</button>
    </form>
    <table class="light-green">
        <c:forEach var="product" items="${productList}">
            <tr>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                        <img class="product-tile" src="${product.imageUrl}" alt="product-title">
                    </a>
                </td>
                <td>
                        ${product.description}
                </td>
                <td class="price">
                    <a onclick="sendToHistoryPage(${product.id})">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>