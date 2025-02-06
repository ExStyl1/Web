package com.web.lab2.lab2.validation;

public class Validate {
    // Метод для проверки, попадает ли точка (x, y) в заданную область с радиусом R
    public static boolean checkPointInArea(float x, float y, float R) {
        // Проверка попадания точки в прямоугольник во 2 четверти
        if (x >= -R && x <= 0.0f && y >= 0.0f && y <= R) {
            return true;
        }
        // Проверка попадания точки в четверть круга в 3 четверти
        else if (x >= -R / 2 && x <= 0.0f && y >= -R / 2 && y <= 0.0f && (x * x + y * y <= (R / 2) * (R / 2))) {
            return true;
        }
        // Проверка попадания точки в треугольник в 4 четверти
        else if (x >= 0.0 && x <= R && y >= (-R / 2) && y <= 0.0 && y >= 0.5 * x - R/2) {
            return true;
        }
        // Если точка не попадает ни в одну из областей
        return false;
    }
    // Метод для проверки, являются ли параметры запроса валидными
    public static boolean validRequestParams(String xParam, String yParam, String rParam) {
        // Проверка на отсутствие null и пустых строк
        return !((xParam == null || yParam == null || rParam == null) || (xParam.isEmpty() || yParam.isEmpty() || rParam.isEmpty()));
    }
}