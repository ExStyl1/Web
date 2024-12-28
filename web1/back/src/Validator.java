import java.util.ArrayList; // Импортируем класс для работы с динамическими массивами
import java.util.Arrays;    // Импортируем класс для работы с массивами
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Validator {
    /**
     * Метод для валидации входных параметров x, y и r.
     *
     * @param x Значение по оси X в виде строки
     * @param y Значение по оси Y в виде строки
     * @param r Радиус в виде строки
     * @return true, если параметры валидны; false в противном случае
     */
    public static boolean validate(String x, String y, String r) {
        try {
            // Преобразуем строку x в число и проверяем его на допустимость
            double dx = Double.parseDouble(x.trim());
            Double[] xValues = {-4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0}; // Допустимые значения для x
            ArrayList<Double> xList = new ArrayList<>(Arrays.asList(xValues));
            if (!xList.contains(dx)) {
                throw new Exception();
            }

            // Преобразуем строку y в число и проверяем его на допустимость
            BigDecimal dy = new BigDecimal(y.trim());
            BigDecimal lowerBound = new BigDecimal("-5");
            BigDecimal upperBound = new BigDecimal("3");
            if (dy.compareTo(lowerBound) < 0 || dy.compareTo(upperBound) > 0) {
                throw new Exception();
            }

            // Преобразуем строку r в число и проверяем его на допустимость
            double dr = Double.parseDouble(r.trim());
            Double[] rValues = {1.0, 2.0, 3.0, 4.0, 5.0}; // Допустимые значения для r
            ArrayList<Double> rList = new ArrayList<>(Arrays.asList(rValues));
            if (!rList.contains(dr)) {
                throw new Exception();
            }

            // Если все проверки пройдены, возвращаем true
            return true;
        } catch (Exception e) {
            // В случае исключения (некорректный ввод) возвращаем false
            return false;
        }
    }
}