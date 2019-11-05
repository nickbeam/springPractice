<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
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
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="list">
        <tr class="${list.excess == true ? 'red' : 'green'}">
            <td><c:out value="${list.dateTime.toString().replaceAll('T', ' ')}"/></td>
            <td><c:out value="${list.description}"/></td>
            <td><c:out value="${list.calories}"/></td>
            <td><a href="meals?action=edit&id=<c:out value="${list.id}"/>">Edit</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${list.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>