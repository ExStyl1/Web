package com.web.lab2.lab2.servlet;

import com.web.lab2.lab2.model.Point;
import com.web.lab2.lab2.model.PointsStorage;
import com.web.lab2.lab2.validation.Validate;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

// Аннотация @WebServlet указывает, что этот класс является сервлетом и определяет его URL-шаблон
@WebServlet(name = "areaCheck")
public class AreaCheckServlet extends HttpServlet {
    // Логгер для записи сообщений в журнал
    private static final Logger log = Logger.getLogger(AreaCheckServlet.class.getName());

    // Переопределение метода doGet для обработки GET-запросов
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    // Метод для обработки запроса
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            // Получаем параметры запроса x, y и r
            float x = Float.parseFloat(request.getParameter("x"));
            float y = Float.parseFloat(request.getParameter("y"));
            float r = Float.parseFloat(request.getParameter("r"));

            // Получаем контекст сервлета
            ServletContext context = getServletContext();
            // Получаем или создаем хранилище точек
            PointsStorage tableData = (PointsStorage) context.getAttribute("tableData");
            if (tableData == null) {
                tableData = new PointsStorage();
                context.setAttribute("tableData", tableData);
            }

            // Засекаем время начала выполнения проверки
            long startTime = System.nanoTime();
            // Проверяем, попадает ли точка в заданную область
            boolean shot = Validate.checkPointInArea(x, y, r);
            // Вычисляем время выполнения проверки
            long executionTime = System.nanoTime() - startTime;
            if (executionTime == 0) {
                executionTime = 1;
            }

            // Преобразуем наносекунды в миллисекунды
            double executionTimeMillis = executionTime / 1_000_000.0;

            // Создаем объект Point с полученными данными
            var point = new Point(x, y, r, executionTimeMillis, shot);
            // Добавляем точку в хранилище
            tableData.addPoint(point);

            // Сохраняем последний запрос в контекст приложения
            context.setAttribute("lastPoint", point);

            // Устанавливаем атрибут запроса для передачи данных на страницу
            request.setAttribute("points", tableData.getPoints());

            // Перенаправляем запрос на страницу answer.jsp
            request.getRequestDispatcher("./answer.jsp").forward(request, response);
        } catch (NumberFormatException | NullPointerException | IllegalStateException e) {
            // Логируем ошибку и отправляем клиенту сообщение об ошибке
            log.warning(e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request loh parameters");
        }
    }
}