<%--
  Created by IntelliJ IDEA.
  User: afshin parhizkari
  Date: 2/17/21
  Time: 8:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<c:if test="${requestScope.ErrorKey ne null}">
    <label>Please give this key to support team: ${requestScope.ErrorKey}</label>
</c:if>
<body>
    there are some error in the page.please contact with administrator
</body>
</html>
