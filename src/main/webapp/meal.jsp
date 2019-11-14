<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Add or Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add or Edit meal</h2>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <c:set var="meal" value="${meal}"/>
        <input type="hidden" name="id" value="<c:out value="${meal.id}"/>">
        <table>
            <tr>
                <td>Date&Time:</td>
                <td><input type="datetime-local" name="dateTime" value="<c:out value="${meal.dateTime}"/>"></td>
            </tr>
            <tr>
                <td>Description:</td>
                <td><input type="text" name="description" value="<c:out value="${meal.description}"/>" size="50"></td>
            </tr>
            <tr>
                <td>Calories:</td>
                <td><input type="number" name="calories" value="<c:out value="${meal.calories}"/>" size="5"></td>
            </tr>
        </table>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
</body>
</html>