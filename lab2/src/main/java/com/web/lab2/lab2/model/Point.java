package com.web.lab2.lab2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Point {
    // Координата X точки
    private float x;

    // Координата Y точки
    private float y;

    // Радиус R, используемый для проверки попадания точки в область
    private float r;

    // Текущее время в формате "yyyy-MM-dd HH:mm:ss"
    private String currentTime;

    // Время выполнения проверки попадания точки в область (в миллисекундах)
    private double execTime;

    // Флаг, указывающий, попала ли точка в заданную область
    private boolean shot;

    // Конструктор класса Point, инициализирующий все поля
    public Point(float x, float y, float r, double execTime, boolean shot) {
        this.x = x;
        this.y = y;
        this.r = r;
        // Устанавливаем текущее время в формате "yyyy-MM-dd HH:mm:ss"
        this.currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.execTime = execTime;
        this.shot = shot;
    }

    // Геттер для координаты X
    public float getX() {
        return x;
    }

    // Геттер для координаты Y
    public float getY() {
        return y;
    }

    // Геттер для радиуса R
    public float getR() {
        return r;
    }

    // Геттер для текущего времени
    public String getCurrentTime() {
        return currentTime;
    }

    // Геттер для времени выполнения проверки
    public double getExecTime() {
        return execTime;
    }

    // Геттер для флага, указывающего на попадание точки в область
    public boolean isShot() {
        return shot;
    }
}