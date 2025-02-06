<%@ page import="com.web.lab2.lab2.model.Point" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Последний запрос</title>
    <link rel="stylesheet" href="./style/answer.css">
    <script defer src="js_scripts/table.js"></script>
</head>
<body>
<main class="content-container">
    <h1>Последний запрос</h1>
    <%
        Point lastPoint = (Point) application.getAttribute("lastPoint");
        if (lastPoint != null) {
    %>
    <table>
        <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Попадание</th>
            <th>Время запроса</th>
            <th>Время выполнения</th>
        </tr>
        <tr>
            <td><%= lastPoint.getX() %>
            </td>
            <td><%= lastPoint.getY() %>
            </td>
            <td><%= lastPoint.getR() %>
            </td>
            <td><%= lastPoint.isShot() %>
            </td>
            <td><%= lastPoint.getCurrentTime() %>
            </td>
            <td><%= lastPoint.getExecTime() %>
            </td>
        </tr>
    </table>
    <% }%>
</main>
<a href="index.jsp">Назад!</a>

</body>
</html>