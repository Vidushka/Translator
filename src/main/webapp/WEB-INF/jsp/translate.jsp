<%--
  Created by IntelliJ IDEA.
  User: hsenid
  Date: 03/05/17
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Translate</title>
</head>
<body>
<form action="/convert" method="post">
    Type word to convert : <input type="text" name="toConvert">
    <select name="fromLanguage">
        <c:forEach items="${languages}" var="language">
            <option value="<c:out value="${language}" />"><c:out value="${language}"/></option>
        </c:forEach>
    </select>
    <br><br>
    <input type="submit" value="Translate">
    <select name="toLanguage">
        <c:forEach items="${languages}" var="language">
            <option value="<c:out value="${language}" />"><c:out value="${language}"/></option>
        </c:forEach>
    </select>
    <br><br>
    Converted word : <input type="text" name="convertedText"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
</body>
</html>
