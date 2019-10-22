<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table border="1">
    <thead>
    <tr>
        <th>Date&Time</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="list">
        <tr>
            <td><c:out value="${list.dateTime}" /></td>
            <td><c:out value="${list.description}" /></td>
            <td><c:out value="${list.calories}" /></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>