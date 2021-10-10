<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="advancedSearchParamList" type="java.util.List" scope="request"/>
<jsp:useBean id="productList" type="java.util.List" scope="request"/>
<jsp:useBean id="errors" type="java.util.Map" scope="request"/>
<jsp:useBean id="advancedSearchParam" type="com.es.phoneshop.model.product.enums.AdvancedSearchParam" scope="request"/>

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
                <td><input type="text" name="advancedDescription" value="${param.advancedDescription}">
                    <select name="advancedParam">
                        <c:forEach var="advancedSearch" items="${advancedSearchParamList}">
                            <c:choose>
                                <c:when test="${advancedSearch eq advancedSearchParam}">
                                    <option value="${advancedSearch}"
                                            selected="selected">${advancedSearch}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${advancedSearch}">${advancedSearch}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Min price:</td>
                <td>
                    <label>
                        <input type="text" name="minPrice" value="${param.minPrice}">
                    </label>
                    <c:if test="${not empty errors['minPrice']}">
                        <div class="error-message">${errors['minPrice']}</div>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>Max price:</td>
                <td>
                    <label>
                        <input type="text" name="maxPrice" value="${param.maxPrice}">
                    </label>
                    <c:if test="${not empty errors['maxPrice']}">
                        <div class="error-message">${errors['maxPrice']}</div>
                    </c:if>
                </td>
            </tr>
        </table>
        <c:if test="${not empty errors['minMaxPrice']}">
            <p>
            <div class="error-message">${errors['minMaxPrice']}</div>
            </p>
        </c:if>
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