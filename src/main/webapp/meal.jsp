<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${""}">
        <table>
            <tr>
                <td>Date&Time:</td>
                <td><input type="datetime-local" name="dateTime"></td>
            </tr>
            <tr>
                <td>Description:</td>
                <td><input type="text" name="description" size="50"></td>
            </tr>
            <tr>
                <td>Calories:</td>
                <td><input type="number" name="calories" value="500" size="5"></td>
            </tr>
        </table>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
</body>
</html>