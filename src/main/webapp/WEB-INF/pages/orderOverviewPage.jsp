<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.product.order.Order" scope="request"/>
<tags:master pageTitle="Overview">
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
    <div class="main-name">Overview</div>
    <p class="success-message">Order successfully added. Great choice!!!</p>
    <tags:orderProductInfo order="${order}"/>
    <table class="light-green" style="width: 25%">
        <tr>
            <td>First name:</td>
            <td>${order.firstName}</td>
        </tr>
        <tr>
            <td>Last name:</td>
            <td>${order.lastName}</td>
        </tr>
        <tr>
            <td>Phone:</td>
            <td>${order.phone}</td>
        </tr>
        <tr>
            <td>Delivery date:</td>
            <td>${order.deliveryDate}</td>
        </tr>
        <tr>
            <td>Delivery address:</td>
            <td>${order.deliveryAddress}</td>
        </tr>
        <tr>
            <td>Payment method:</td>
            <td>${order.paymentMethod}</td>
        </tr>
    </table>
</tags:master>