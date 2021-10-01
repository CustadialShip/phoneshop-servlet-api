<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="order" required="true" type="com.es.phoneshop.model.product.order.Order" %>
<div class="content">
    <c:if test="${not empty order.cartItems}">
        <section><p>
            <table class="light-green">
                <tr>
                    <td>Image</td>
                    <td>Description</td>
                    <td>Quantity</td>
                    <td>Price</td>
                </tr>
                <c:forEach var="cartItem" items="${order.cartItems}" varStatus="status">
                    <tr>
                        <td><img class="product-tile" src="${cartItem.cartProduct.imageUrl}"
                                 alt="image of product"></td>
                        <td>
                            <a onclick="sendToPDP(${cartItem.cartProduct.id})">
                                    ${cartItem.cartProduct.description} <br>
                            </a>
                        </td>
                        <td>
                                ${cartItem.quantity}
                        </td>
                        <td>
                            <a onclick="sendToHistoryPage(${cartItem.cartProduct.id})">
                                <c:set var="currency" value="${cartItem.cartProduct.currency.symbol}"/>
                                <fmt:formatNumber value="${cartItem.cartProduct.price}" type="currency"
                                                  currencySymbol="${cartItem.cartProduct.currency.symbol}"/>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </section>
    </c:if>
</div>
<div class="block-name">Delivery info</div>
<p>Total quantity: ${order.totalQuantity}
<p>Subtotal price: <fmt:formatNumber value="${order.subtotalPrice}" type="currency"
                                     currencySymbol="${currency}"/></p>
<p>Delivery price: <fmt:formatNumber value="${order.deliveryPrice}" type="currency"
                                     currencySymbol="${currency}"/></p>