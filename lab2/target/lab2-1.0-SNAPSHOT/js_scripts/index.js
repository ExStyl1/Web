const form = document.getElementById("pointForm");
const y_value = document.getElementById("y");
const x_value = document.getElementById("x");
let r_global = 0.0;
// При загрузке страницы выполняются следующие действия:
window.addEventListener('load', () => {
    // Отрисовка осей координат
    drawAxes(centerX, centerY);
    // Создание новой страницы для таблицы
    createNewTablePage();
    // Загрузка данных таблицы
    loadTableData();

    // Добавление обработчика события для кнопок R
    let rButtons = document.querySelectorAll("#r button");
    rButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            try {
                const selectedValue = parseFloat(button.textContent); // Получаем значение кнопки
                document.getElementById('radiusInput').value = selectedValue; // Устанавливаем значение в скрытый инпут
                r_global = selectedValue; // Сохраняем значение в глобальную переменную
                clearContent(); // Очищаем содержимое (если необходимо)
            } catch (error) {
                console.error(error.message);
            }
        });
    });
    // Добавление обработчика события при изменении значения X
    x_value.addEventListener("change", () => {
        let x = document.querySelector('input[name="x"]:checked');
        if (!x) {
            alert("Fill in the field for X");
            x_value.classList.add("invalid-input");
            x_value.classList.remove("valid-input");
            clearContent();
            return;
        }
        const allowedXValues = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];
        let xNumber = parseFloat(x.value);
        if (!allowedXValues.includes(xNumber)) {
            alert("Invalid value for X. Please select a valid value from the list.");
            x_value.parentElement.classList.add("invalid-input");
            x_value.parentElement.classList.remove("valid-input");
            clearContent();
            return;
        }
        x_value.parentElement.classList.add("valid-input");
        x_value.parentElement.classList.remove("invalid-input");
        clearContent();
    });
    // Добавление обработчика события при изменении значения Y
    y_value.addEventListener("change", () => {
        const y = y_value.value.trim();
        if (!y) {
            alert("Fill in the field for Y");
            y_value.classList.add("invalid-input");
            y_value.classList.remove("valid-input");
            clearContent();
            return;
        }
        if (isNaN(y)) {
            alert("The input does not match the format. Please enter a valid number.");
            y_value.classList.add("invalid-input");
            y_value.classList.remove("valid-input");
            clearContent();
            return;
        }
        const yNumber = parseFloat(y);
        if (y.includes(".") && y.split(".")[1].length > 4) {
            alert("Y value can have at most 4 decimal places.");
            y_value.classList.add("invalid-input");
            y_value.classList.remove("valid-input");
            clearContent();
            return;
        }
        if (yNumber < -3.0 || yNumber > 5.0) {
            alert("Y is out of bounds (allowed range -3.0 - 5.0)");
            y_value.classList.add("invalid-input");
            y_value.classList.remove("valid-input");
            clearContent();
            return;
        }
        y_value.classList.add("valid-input");
        y_value.classList.remove("invalid-input");
        clearContent();
    });
    // Добавление обработчика события при отправке формы
    form.addEventListener("submit", function (event) {
        event.preventDefault();
        // Получение значений X, Y и R
        let x = document.querySelector('input[name="x"]:checked');
        let y = y_value.value.trim();
        let r = document.getElementById('radiusInput').value; // Получаем значение R из скрытого инпута
        // Проверка X
        if (!x) {
            alert("Fill in the field for X");
            clearContent();
            return;
        }
        const allowedXValues = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];
        let xNumber = parseFloat(x.value);
        if (!allowedXValues.includes(xNumber)) {
            alert("Invalid value for X. Please select a valid value from the list.");
            clearContent();
            return;
        }
        // Проверка Y
        if (!y) {
            alert("Fill in the field for Y");
            clearContent();
            return;
        }
        if (isNaN(y)) {
            alert("The input does not match the format. Please enter a valid number.");
            clearContent();
            return;
        }
        const yNumber = parseFloat(y);
        if (y.includes(".") && y.split(".")[1].length > 4) {
            alert("Y value can have at most 4 decimal places.");
            clearContent();
            return;
        }
        if (yNumber < -3.0 || yNumber > 5.0) {
            alert("Y is out of bounds (allowed range -3.0 - 5.0)");
            clearContent();
            return;
        }
        // Проверка R
        if (!r) {
            alert("Select R value");
            clearContent();
            return;
        }
        const allowedRValues = [1, 1.5, 2, 2.5, 3];
        let rNumber = parseFloat(r);
        if (!allowedRValues.includes(rNumber)) {
            alert("Invalid value for R. Please select a valid value from the list.");
            clearContent();
            return;
        }
        // Сохраняем координаты точки
        points.push({ x: xNumber, y: yNumber, r: rNumber });
        // Сохранение точек в sessionStorage
        sessionStorage.setItem("points", JSON.stringify(points));
        // Если все проверки пройдены, рисуем точку и отправляем форму
        drawPoint(xNumber, yNumber);
        setTimeout(() => {
            event.target.submit();
        }, 1000);
    });
    console.log("The page is fully loaded");
});
/*/ Функция для очистки всех точек (если нужно)
function clearPoints() {
    points = []; // Очищаем массив точек
    localStorage.removeItem("points"); // Удаляем точки из localStorage
    clearContent(); // Перерисовываем canvas без точек
}*/
