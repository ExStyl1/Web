package com.web.lab2.lab2.model;

import jakarta.ejb.Stateful;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Аннотация @Stateful указывает, что этот класс является stateful EJB (Enterprise Java Bean)
// Аннотация @ApplicationScoped указывает, что экземпляр этого класса будет жить в течение всего жизненного цикла приложения
@Stateful
@ApplicationScoped
public class PointsStorage implements Serializable {
    // Список точек, которые будут храниться в этом хранилище
    private final List<Point> points;

    // Конструктор класса PointsStorage, инициализирующий список точек
    public PointsStorage() {
        this.points = new ArrayList<>();
    }

    // Метод для добавления новой точки в список
    public void addPoint(Point point) {
        points.add(point);
    }

    // Метод для получения списка всех точек, хранящихся в этом хранилище
    public List<Point> getPoints() {
        return points;
    }

    // Метод для получения точек, соответствующих заданному радиусу (r)
    public List<Point> getPointsByRadius(double r) {
        return points.stream()
                .filter(point -> point.getR() == r) // Фильтруем точки по радиусу
                .collect(Collectors.toList());
    }

    // Очистка всех точек (опционально)
    public void clearPoints() {
        points.clear();
    }
}