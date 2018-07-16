<%--
    Document   : confirmation
    Created on : Jul 1, 2018 3:16:55 PM
    Author     : Jon Sobier
--%>


            <div id="singleColumn">

                <p id="confirmationText">
        <strong>Your order has been successfully processed and will be shipped within the next 5-7 business days.</strong>
        <br><br>
        <strong style="color:goldenrod;">Please find a safe place to store your confirmation number:</strong>
        <br>
        <strong style="font-size:x-large; color:red;">${orderRecord.confirmationNumber}</strong>
        <br><br>
        If you have any questions, queries, concerns, or complaints about your experience 
        buying one of these Classic American Muscle Cars, please feel free to contact
        <br><br>
        <a href="mailto:jonsobier@outlook.com">The Kensington Trust</a>
        <br><br>
        and we will respond soon after depending on the urgency of your needs.
        <br><br>
        Thank you for purchasing a Classic American Muscle Car from The Kensington Trust, and
        thank you for your donation to a worthy and important cause. Thank you!!!
        <br><br>
        <strong style="font-size:x-large; color:goldenrod;">The Kensington Family</strong>
    </p>

                <div class="summaryColumn" >

                    <table id="orderSummaryTable" class="detailsTable" >
                        <tr class="header">
                            <th colspan="3" style="padding:10px">Order Summary</th>
                        </tr>
                    <tr class="tableHeading">
                <td colspan="3" style="text-align: center;">Price</td>
            </tr>
<c:forEach var="orderedCar" items="${orderedCars}" varStatus="iter">

                <tr class="${((iter.index % 2) != 0) ? 'white' : 'white'}">
                  
                    <td class="confirmationPriceColumn">
                        &dollar; ${cars[iter.index].price * orderedCar.quantity}
                    </td>
                </tr>

            </c:forEach>
<tr class="black"><td colspan="3" style="padding: 0 20px"><hr></td></tr>

            <tr class="black">
                <td colspan="2" id="deliverySurchargeCellLeft"><strong>Delivery Surcharge:</strong></td>
                <td id="deliverySurchargeCellRight">&dollar; ${initParam.deliverySurcharge}</td>
            </tr>

            <tr class="black">
                <td colspan="2" id="totalCellLeft"><strong>Total:</strong></td>
                <td id="totalCellRight">&dollar; ${orderRecord.amount}</td>
            </tr>

            <tr class="black"><td colspan="3" style="padding: 0 20px"><hr></td></tr>

            <tr class="black">
                <td colspan="3" id="dateProcessedRow"><strong>Date Processed:</strong>
                    ${orderRecord.dateCreated}
                </td>
            </tr>
        </table>

    </div>
                <div class="summaryColumn">
                    
                <table id="deliveryAddressTable" class="detailsTable">
                        <tr class="header">
                            <th colspan="3" style="padding:10px">Delivery Address</th>
                        </tr>
            <tr>
                <td colspan="3" class="black">
                    ${customer.name}
                    <br>
                    ${customer.address}
                    <br>
                    ${customer.state}, USA 
                    <br>
                    <hr>
                    <strong>Email:</strong> ${customer.email}
                    <br>
                    <strong>Phone:</strong> ${customer.phone}
                </td>
            </tr>
        </table>

    </div>
</div>