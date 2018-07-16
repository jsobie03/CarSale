<%--
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software
 * except in compliance with the terms of the license at:
 * http://developer.sun.com/berkeley_license.html

 * author: Jon Sobier
--%>


<form action="<c:url value='j_security_check'/>" method=post>
    <div id="loginBox">
        <p><strong>Username:</strong>
            <input type="text" size="20" name="j_username"></p>

        <p><strong>Password:</strong>
            <input type="password" size="20" name="j_password"></p>

        <p><input type="submit" value="submit"></p>
    </div>
</form>
