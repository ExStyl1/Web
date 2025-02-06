package com.web.lab2.lab2.servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;
import static com.web.lab2.lab2.validation.Validate.validRequestParams;
// Аннотация @WebServlet указывает, что этот класс является сервлетом и определяет его URL-шаблон
@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    // Логгер для записи сообщений в журнал
    private static final Logger log = Logger.getLogger(ControllerServlet.class.getName());
    // Переопределение метода doGet для обработки Post-запросов
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    // Метод для обработки запроса
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем параметры запроса x, y и r
        String xParam = request.getParameter("x");
        String yParam = request.getParameter("y");
        String rParam = request.getParameter("r");
        System.out.println("xParam: " + xParam + ", yParam: " + yParam + ", rParam: " + rParam);
        // Проверяем, являются ли параметры запроса валидными
        if (!validRequestParams(xParam, yParam, rParam)) {
            // Логируем ошибку и отправляем клиенту сообщение об ошибке
            log.warning("Invalid request 123 parameters: x=" + xParam + ", y=" + yParam + ", r=" + rParam);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request loh1 parameters: x=" + xParam + ", y=" + yParam + ", r=" + rParam);
            return;
        }
        // Получаем контекст сервлета
        ServletContext context = getServletContext();
        // Перенаправляем запрос на сервлет "areaCheck"
        context.getNamedDispatcher("areaCheck").forward(request, response);
    }
}