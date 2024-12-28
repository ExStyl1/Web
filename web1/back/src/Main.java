import com.fastcgi.*;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        // Создаем экземпляр интерфейса FastCGI
        var fcgiInterface = new FCGIInterface();

        // Бесконечный цикл для обработки входящих запросов
        while (fcgiInterface.FCGIaccept() >= 0) {
            // Получаем строку запроса из системного свойства
            String query = System.getProperty("QUERY_STRING");

            // Парсим тело запроса в виде пар ключ-значение
            Map<String, String> request = Request.parseRequestBody(query);

            // Создаем таймер для измерения времени обработки запроса
            Timer timer = new Timer();
            timer.start();

            // Проверяем, корректны ли входные данные
            if (Validator.validate(request.get("x_field"), request.get("y_field"), request.get("R_field"))) {
                // Преобразуем входные данные в числовые значения и проверяем попадание точки
                String result = HitChecker.trueOrFalse(
                        Double.parseDouble(request.get("x_field")),
                        Double.parseDouble(request.get("y_field")),
                        Double.parseDouble(request.get("R_field"))
                );

                // Формируем успешный JSON-ответ с результатом и временем обработки
                String jsonResponse = Response.successResponse(result, timer.getElapsedTime());
                String response = Response.buildResponse("200 OK", jsonResponse);
                System.out.println(response); // Отправляем ответ
            } else {
                // Формируем ответ об ошибке, если входные данные некорректны
                String jsonResponse = Response.errorResponse("400 Bad Request");
                String response = Response.buildResponse("400 Bad Request", jsonResponse);
                System.out.println(response); // Отправляем ответ
            }
        }
    }
}