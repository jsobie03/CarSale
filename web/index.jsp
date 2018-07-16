<%-- 
* author: Jon Sobier
--%>


<%-- Set session-scoped variable to track the view user is coming from.
     This is used by the language mechanism in the Controller so that
     users view the same page when switching between English and Chinese (traditional). --%>
     
<c:set var='view' value='/index' scope='session' />

  
<div>
    <h1 class="fancy1"><fmt:message key='indexHeader' /></h1>
                <h2 class="fancy2"><fmt:message key='subHeader' /></h2>
</div>   
<%-- HTML markup starts below --%>

<div id="indexLeftColumn">
    <div id="welcomeText">
        <p style="font-size: 26px; font-weight: bold;"><fmt:message key='greeting' /></p>
        <img src="img/rolandKensington.jpg"/>
        <p><fmt:message key='introText' /></p>
    </div>
</div>

<div id="indexRightColumn">
    <c:forEach var="make" items="${makes}">
        <div class="makeBox">
            <a href="<c:url value='make?${make.id}'/>">
                <span class="makeLabel"></span>
                <span class="makeLabelText"><fmt:message key='${make.name}'/></span>

                <img src="${initParam.makeImagePath}${make.name}.png"
                     alt="<fmt:message key='${make.name}'/>" class="makeImage">
            </a>
        </div>
    </c:forEach>
</div>