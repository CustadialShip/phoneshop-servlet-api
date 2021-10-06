<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.product.order.Order" scope="request"/>
<jsp:useBean id="paymentMethods" type="java.util.List" scope="request"/>
<tags:master pageTitle="Checkout">
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
    <div class="main-name">Checkout</div>
    <c:choose>
        <c:when test="${not empty errorsMap}">
            <p class="error-message" style="font-size: large">
                Some problems with adding product to cart
            </p>
        </c:when>
        <c:when test="${not empty param.successMessage}">
            <p class="success-message" style="font-size: large">
                    ${param.successMessage}
            </p>
        </c:when>
    </c:choose>
    <tags:orderProductInfo order="${order}"/>
    <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
        <table class="light-green" style="width: 25%">
            <tags:rowInfoCheckout label="First name" name="firstName" errors="${errorsMap}"
                                  order="${order}" previousName="${order.firstName}"/>
            <tags:rowInfoCheckout label="Last name" name="lastName" errors="${errorsMap}"
                                  order="${order}" previousName="${order.lastName}"/>
            <tags:rowInfoCheckout label="Phone" name="phone" errors="${errorsMap}"
                                  order="${order}" previousName="${order.phone}"/>
            <tags:dateRowCheckout order="${order}" errorsMap="${errorsMap}"/>
            <tags:rowInfoCheckout label="Delivery address" name="deliveryAddress" errors="${errorsMap}"
                                  order="${order}" previousName="${order.deliveryAddress}"/>
            <tr>
                <td>Payment method:</td>
                <td>
                    <label>
                        <select name="paymentMethod">
                            <option></option>
                            <c:forEach var="paymentMethod" items="${paymentMethods}">
                                <c:choose>
                                    <c:when test="${paymentMethod eq order.paymentMethod}">
                                        <option value="${paymentMethod}" selected="selected">${paymentMethod}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${paymentMethod}">${paymentMethod}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </label>
                    <c:set var="error" value="${errorsMap['paymentMethod']}"/>
                    <c:if test="${not empty error}">
                        <div class="error-message">${error}</div>
                    </c:if>
                </td>
            </tr>
        </table>
        <p>
            <button>Confirm order</button>
        </p>
    </form>

</tags:master>