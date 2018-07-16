<%--
    Document   : cart
    Created on : June 21, 2018
    Author     : Jon Sobier
--%>


<div id="singleColumn">

    <c:choose>
        <c:when test="${cart.numberOfItems > 1}">
            <p><fmt:message key="yourCartContains"/> ${cart.numberOfItems} <fmt:message key="items"/>.</p>
        </c:when>
        <c:when test="${cart.numberOfItems == 1}">
            <p><fmt:message key="yourCartContains"/> ${cart.numberOfItems} <fmt:message key="item"/>.</p>
        </c:when>
        <c:otherwise>
            <p><fmt:message key="yourCartEmpty"/></p>
        </c:otherwise>
    </c:choose>

    <div id="actionBar">
        <%-- clear cart widget --%>
        <c:if test="${!empty cart && cart.numberOfItems != 0}">

            <c:url var="url" value="viewCart">
                <c:param name="clear" value="true"/>
            </c:url>

            <a href="${url}" class="bubble hMargin">clear cart</a>
        </c:if>

        <%-- continue shopping widget --%>
        <c:set var="value">
            <c:choose>
                <%-- if 'selectedMake' session object exists, send user to previously viewed category --%>
                <c:when test="${!empty selectedMake}">
                    make
                </c:when>
                <%-- otherwise send user to welcome page --%>
                <c:otherwise>
                    index.jsp
                </c:otherwise>
            </c:choose>
        </c:set>

        <c:url var="url" value="${value}"/>
        <a href="${url}" class="bubble hMargin">CONTINUE SHOPPING</a>

        <%-- checkout widget --%>
        <c:if test="${!empty cart && cart.numberOfItems != 0}">
            <a href="<c:url value='checkout'/>" class="bubble hMargin">Proceed To Checkout &#x279f;</a>
        </c:if>
    </div>

    <c:if test="${!empty cart && cart.numberOfItems != 0}">

      <h4 id="subtotal">SUBTOTAL: &dollar; ${cart.subtotal}</h4>

      <table id="cartTable">

        <tr class="header">
            <th>PHOTO</th>
            <th>YEAR</th>
            <th>MODEL</th>
            <th>PRICE</th>
            <th>UPDATE ORDER</th>
        </tr>

        <c:forEach var="cartItem" items="${cart.items}" varStatus="iter">

          <c:set var="car" value="${cartItem.car}"/>

          <tr class="${((iter.index % 2) == 0) ? 'black' : 'black'}">

    <td><img id="cartImg" src="${initParam.carImagePath}${car.name}.jpg" alt="${car.name}"></td>            
    <td>${car.year}</td>          
    <td>${car.name}</td>
    <td>&dollar;${cartItem.total}</td>
    <td>
                <form action="<c:url value='updateCart'/>" method="post">
                    <input type="hidden"
                           name="carId"
                           value="${car.id}">
                    <input type="text"
                           maxlength="2"
                           size="2"
                           value="${cartItem.quantity}"
                           name="quantity"
                           style="margin:5px">
                    <input type="submit"
                           name="submit"
                           value="Update Order">
                </form>
            </td>
          </tr>

        </c:forEach>

      </table>

    </c:if>
</div>
