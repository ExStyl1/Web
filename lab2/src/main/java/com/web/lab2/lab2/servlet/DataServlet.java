package com.web.lab2.lab2.servlet;
import com.web.lab2.lab2.model.Point;
import com.web.lab2.lab2.model.PointsStorage;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
// Аннотация @WebServlet указывает, что этот класс является сервлетом и определяет его URL-шаблон
@WebServlet(name = "DataServlet", urlPatterns = "/data")
public class DataServlet extends HttpServlet {
    // Переопределение метода doGet для обработки GET-запросов
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Получаем контекст сервлета
        ServletContext context = getServletContext();
        // Получаем хранилище точек из контекста
        PointsStorage tableData = (PointsStorage) context.getAttribute("tableData");
        // Если хранилище точек существует, отправляем данные в формате JSON
        if (tableData != null) {
            List<Point> points = tableData.getPoints();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(toJson(points));
            out.flush();
        } else {
            // Если хранилище точек не существует, отправляем статус 404 (Not Found)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    // Метод для преобразования списка точек в строку JSON
    private String toJson(List<Point> points) {
        StringBuilder json = new StringBuilder("[");
        for (Point point : points) {
            json.append("{")
                    .append("\"x\":").append(point.getX()).append(",")
                    .append("\"y\":").append(point.getY()).append(",")
                    .append("\"r\":").append(point.getR()).append(",")
                    .append("\"shot\":").append(point.isShot()).append(",")
                    .append("\"currentTime\":\"").append(point.getCurrentTime()).append("\",")
                    .append("\"execTime\":").append(point.getExecTime())
                    .append("},");
        }
        // Удаляем последнюю запятую, если список точек не пуст
        if (!points.isEmpty()) {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("]");
        return json.toString();
    }
}