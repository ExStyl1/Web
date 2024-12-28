import java.nio.charset.StandardCharsets; // Импортируем стандартные кодировки

public class Response {
    /**
     * Метод для построения HTTP-ответа.
     *
     * @param status     Статус ответа (например, "200 OK", "400 Bad Request")
     * @param jsonContent Содержимое ответа в формате JSON
     * @return Строка, представляющая полный HTTP-ответ
     */
    public static String buildResponse(String status, String jsonContent) {
        // Шаблон HTTP-ответа
        final String HTTP_TEMPLATE = """
            HTTP/1.1 %s
            Content-Type: application/json
            Content-Length: %d
            
            %s
            """;
        // Форматируем и возвращаем HTTP-ответ
        return String.format(HTTP_TEMPLATE,
                status,
                jsonContent.getBytes(StandardCharsets.UTF_8).length + 2, // +2 для учета символов CRLF
                jsonContent);
    }

    /**
     * Метод для создания успешного JSON-ответа.
     *
     * @param hitResult Результат проверки попадания точки
     * @param execTime  Время выполнения запроса
     * @return Строка, представляющая JSON-ответ с результатом
     */
    public static String successResponse(String hitResult, String execTime) {
        // Шаблон успешного JSON-ответа
        String RESULT_JSON = """
            {
                "hit": "%s",
                "exec_time": "%s"
            }
            """;
        // Форматируем и возвращаем JSON-ответ
        return String.format(RESULT_JSON, hitResult, execTime);
    }

    /**
     * Метод для создания JSON-ответа об ошибке.
     *
     * @param reason Причина ошибки
     * @return Строка, представляющая JSON-ответ с причиной ошибки
     */
    public static String errorResponse(String reason) {
        // Шаблон JSON-ответа об ошибке
        String ERROR_JSON = """
            {
                "reason": "%s"
            }
            """;
        // Форматируем и возвращаем JSON-ответ об ошибке
        return String.format(ERROR_JSON, reason);
    }
}