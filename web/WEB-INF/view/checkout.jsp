<%--
    Document   : checkout
    Created on : Jun 16, 2018, 3:59:32 PM
    Author     : Jon Sobier
--%>

<div id="singleColumn">

    <h1 style="text-align: center;">
        Checkout
    </h1>

    <p style="text-align:center; font-size:24px; color:white;">
        In order to purchase the items in your shopping cart, 
        please provide us with the following information:
    </p>

    <div id="infoBox">
        <ul>
            <li>Next-day delivery is not available</li>
            <li>A &dollar; ${initParam.deliverySurcharge}
                delivery surcharge is applied to all purchase orders that must be delivered!</li>
            <li>All proceeds go to The Society to Preserve Classic American Muscle Cars which was a favorite of Mr. Kensington's.</li>
            <li>All purchases are final and there are absolutely no exceptions to this rule.</li>
        </ul>


        <form action="<c:url value='purchase'/>" method="post">
            <table id="checkoutTable">
                <tr>
                    <td><label for="name">Name:</label></td>
                    <td class="inputField">
                        <input type="text"
                               size="31"
                               maxlength="45"
                               id="name"
                               name="name"
                               value="${param.name}">
                    </td>
                </tr>
                <tr>
                    <td><label for="email">Email:</label></td>
                    <td class="inputField">
                        <input type="text"
                               size="31"
                               maxlength="45"
                               id="email"
                               name="email"
                               value="${param.email}">
                    </td>
                </tr>
                <tr>
                    <td><label for="phone">Phone:</label></td>
                    <td class="inputField">
                        <input type="text"
                               size="31"
                               maxlength="16"
                               id="phone"
                               name="phone"
                               value="${param.phone}">
                    </td>
                </tr>
                <tr>
                    <td><label for="address">Address:</label></td>
                    <td class="inputField">
                        <input type="text"
                               size="31"
                               maxlength="45"
                               id="address"
                               name="address"
                               value="${param.address}">
                    </td>
                </tr>
                                <tr>
                    <td><label for="city">City:</label></td>
                    <td class="inputField">
                        <input type="text"
                               size="30"
                               maxlength="25"
                               id="city"
                               name="city"
                               value="${param.city}">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="state">State:</label>
                    </td>
                    <td class="inputField">
                        <input type="text"
                               size="6"
                               maxlength="10"
                               id="state"
                               name="state"
                               value="${param.state}">
                    </td>
                </tr>
                <tr>
                    <td><label for="creditcard">Credit Card Number:</label></td>
                    <td class="inputField">
                        <input type="text"
                               size="31"
                               maxlength="19"
                               id="creditcard"
                               name="creditcard"
                               value="${param.creditcard}">
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="submit purchase">
                    </td>
                </tr>
            </table>
        </form> 

        <table id="priceBox">
            <tr>
                <td>Subtotal:</td>
                <td class="checkoutPriceColumn">
                    &dollar; ${cart.subtotal}</td>
            </tr>
            <tr>
                <td>Delivery Surcharge:</td>
                <td class="checkoutPriceColumn">
                    &dollar; ${initParam.deliverySurcharge}</td>
            </tr>
            <tr>
                <td class="total">Total:</td>
                <td class="total checkoutPriceColumn">
                    &dollar; ${cart.total}</td>
            </tr>
        </table>
    </div> 
</div>