import java.time.Duration; // Импортируем класс для работы с продолжительностью времени
import java.time.Instant;  // Импортируем класс для работы с моментами времени

public class Timer {
    private Instant startTime; // Переменная для хранения времени начала

    /**
     * Метод для запуска таймера.
     * Сохраняет текущее время как время начала.
     */
    public void start() {
        startTime = Instant.now(); // Запоминаем текущее время
    }

    /**
     * Метод для получения прошедшего времени с момента запуска таймера.
     *
     * @return Строка, представляющая прошедшее время в секундах
     */
    public String getElapsedTime() {
        Instant endTime = Instant.now(); // Получаем текущее время как время окончания
        // Вычисляем разницу между временем окончания и началом и возвращаем в секундах
        return String.valueOf((Duration.between(startTime, endTime).toNanos() / Math.pow(10, 9)));
    }
}