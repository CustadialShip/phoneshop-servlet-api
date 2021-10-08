<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.product.cart.Cart" scope="request"/>
<a style="font-size: x-large" href="${pageContext.servletContext.contextPath}/cart">
    Cart: total quantity - ${cart.totalQuantity} | total price - ${cart.totalPrice}
</a>
<a href="${pageContext.servletContext.contextPath}/advancedSearch" style="font-size: xx-large">Advanced search</a>