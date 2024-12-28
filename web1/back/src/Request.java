import java.net.URLDecoder; // Импортируем класс для декодирования URL
import java.nio.charset.StandardCharsets; // Импортируем стандартные кодировки
import java.util.HashMap; // Импортируем класс для работы с хэш-таблицами
import java.util.Map; // Импортируем интерфейс Map

public class Request {
    // Метод для разбора тела запроса в формате "key1=value1&key2=value2"
    public static Map<String, String> parseRequestBody(String request) {
        // Создаем хэш-таблицу для хранения пар ключ-значение
        var queryPairs = new HashMap<String, String>();

        // Разбиваем строку запроса на отдельные пары по символу '&'
        var pairs = request.split("&");

        // Проходим по каждой паре
        for (var pair : pairs) {
            // Находим индекс символа '=' для разделения ключа и значения
            var idx = pair.indexOf("=");

            // Декодируем ключ и значение, добавляем их в хэш-таблицу
            queryPairs.put(
                    URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8), // Декодируем ключ
                    URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8) // Декодируем значение
            );
        }

        // Возвращаем хэш-таблицу с парами ключ-значение
        return queryPairs;
    }
}