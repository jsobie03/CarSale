<%--
    Document   : make.jsp
    Created on : Jun 09, 2010, 3:59:32 PM
    Updated on : Jun 24, 2018, 3:47:55 PM
    Author     : Jon Sobier
--%>



<%--<div id="makeLeftColumn">--%>
<div id="singleColumn">
    <c:forEach var="make" items="${makes}">

        <c:choose>
            <c:when test="${make.name == selectedMake.name}">
                <div class="makeButton" id="selectedMake">
                    <span class="makeText">${make.name}</span>
                </div>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='make?${make.id}'/>" class="makeButton">
                    <span class="makeText">
                        ${make.name}
                    </span>
                </a>
            </c:otherwise>
        </c:choose>

    </c:forEach>

    <%--<div id="makeRightColumn">
      <h1 class="makeHeader"><fmt:message key="${selectedMake.name}" />${selectedMake.name}</h1> --%>

    <table id="carTable">
        <tr>
            <th>Photo</th>
            <th>Year / Make / Model</th>
            <th>Vehicle Id# / Mileage</th>
            <th>Body Style</th>
            <th>Transmission</th>
            <th>Price</th>
        </tr>
        <c:forEach var="car" items="${makeCars}" varStatus="iter">

            <tr class="${((iter.index % 2) == 0)? 'black':'black'}">  

                <td>  
                    <img src="${initParam.carImagePath}${car.name}.jpg" id="makeImg" 
                         alt="${car.name}"/>
                </td>

                <td>
                    <span class="mediumText" style="color:red;">${car.year}</span><br>
                    <span class="largeText" style="color:white">${selectedMake.name}</span><br>
                    <span class="largeText" style="color:white">${car.name}</span>
                </td>
                
                <td><span>VIN#: ${car.vin}</span><br>
                    <br>
                    <span>Odometer: ${car.mileage} miles</span>
                </td>
                
                <td>${car.body}</td>
                <td>${car.trans}</td>
                <td class="largeText" style="color:springgreen;">&dollar; ${car.price}<br>
                    <form action="<c:url value='addToCart'/>" method="POST">
                        <input type="hidden"
                               name="carId"
                               value="${car.id}">
                        <input type="submit"
                               name="submit"
                               value="add to cart">
                    </form>
                </td>

            </tr>          
        </c:forEach>   
    </table>
</div>

