<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <colgroup>
        <col span="3" style="background:Khaki">
    </colgroup>
    <tr>
        <th>DATE_TIME
        </td>
        <th>DESCRIPTION
        </td>
        <th>CALORIES
        </td>
    </tr>
    <tr>
        <c:forEach items="${requestScope.meals}" var="d">
    <tr style="background-color:${(d.excess ? 'red' : 'greenyellow')}">
        <td><fmt:parseDate value="${d.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" type="both"
                           var="parsedDateTime"/>
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}"/>
        </td>
        <td>${d.description}</td>
        <td>${d.calories}</td>
    </tr>
    </c:forEach>
    </tr>
</table>
</body>
</html>
