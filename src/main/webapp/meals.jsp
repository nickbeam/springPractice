<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="MealsUtil" class="ru.javawebinar.topjava.util.MealsUtil" scope="page" />
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
    <c:forEach items="${MealsUtil.meals}" var="meals">
        <tr>
            <td>${meals.datetime}</td>
            <td>${meals.description}</td>
            <td>${meals.calories}</td>
        </tr>
    </c:forEach>
    <tr>
        <td></td>
    </tr>
    </tbody>
</table>

</body>
</html>