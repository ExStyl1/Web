<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>main</title>
    <link rel="stylesheet" href="style/main.css">
    <script defer src="js_scripts/graph.js"></script>
    <script defer src="js_scripts/index.js"></script>
    <script defer src="js_scripts/table.js"></script>
</head>
<body>
<header>
    <h1>Point Checker</h1>
    <div class="student-info">
        Student: Шевелев Антон<br>
        Group: P3214<br>
        Variant: 311490
    </div>
</header>
<main>
    <div class="content-container">
        <canvas class="horizontal" id="canvas"></canvas>
        <form class="horizontal" action="${pageContext.request.contextPath}/controller" method="GET" id="pointForm">
            <!-- X coordinate (радиокнопки) -->
            <label>X coordinate:</label>
            <div class="radio-group" id="x">
                <div class="radio-item">
                    <label for="x--2">-2</label>
                    <input type="radio" id="x--2" name="x" value="-2">
                </div>
                <div class="radio-item">
                    <label for="x--1.5">-1.5</label>
                    <input type="radio" id="x--1.5" name="x" value="-1.5">
                </div>
                <div class="radio-item">
                    <label for="x--1">-1</label>
                    <input type="radio" id="x--1" name="x" value="-1">
                </div>
                <div class="radio-item">
                    <label for="x--0.5">-0.5</label>
                    <input type="radio" id="x--0.5" name="x" value="-0.5">
                </div>
                <div class="radio-item">
                    <label for="x-0">0</label>
                    <input type="radio" id="x-0" name="x" value="0">
                </div>
                <div class="radio-item">
                    <label for="x-0.5">0.5</label>
                    <input type="radio" id="x-0.5" name="x" value="0.5">
                </div>
                <div class="radio-item">
                    <label for="x-1">1</label>
                    <input type="radio" id="x-1" name="x" value="1">
                </div>
                <div class="radio-item">
                    <label for="x-1.5">1.5</label>
                    <input type="radio" id="x-1.5" name="x" value="1.5">
                </div>
                <div class="radio-item">
                    <label for="x-2">2</label>
                    <input type="radio" id="x-2" name="x" value="2">
                </div>

            </div>
            <input type="hidden" id="xInput" name="x">

            <!-- Y coordinate (текстовое поле) -->
            <label for="y">Y coordinate:</label>
            <input type="text" id="y" name="y" required pattern="[+-]?(\d+(\.\d*)?|\.\d+)" min="-3" max="5"
                   placeholder="Enter a number from -3 to 5">

            <!-- Radius (кнопки) -->
            <label>Radius:</label>
            <div class="button-group" id="r">
                <button type="button" onclick="setRadius(1)">1</button>
                <button type="button" onclick="setRadius(1.5)">1.5</button>
                <button type="button" onclick="setRadius(2)">2</button>
                <button type="button" onclick="setRadius(2.5)">2.5</button>
                <button type="button" onclick="setRadius(3)">3</button>
            </div>
            <input type="hidden" id="radiusInput" name="r" value="1"> <!-- Скрытый инпут для R -->

            <button id="checkButton" type="submit">Check Point</button>
        </form>
        <div class="table-container"></div>
    </div>
    <div class="button-container">
        <button onclick="prevPage()">Previous</button>
        <button onclick="nextPage()">Next</button>
    </div>
</main>
<footer id="copyright">i have no rights</footer>

<script>
    // Функция для установки значения радиуса
    function setRadius(value) {
        document.getElementById('radiusInput').value = value;
    }
</script>
</body>
</html>