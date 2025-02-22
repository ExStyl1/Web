package com.example.demo.EJBbeans;

import com.example.demo.entities.ResultEntity;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class ResultEJB {

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    /**
     * Добавление нового результата в базу данных.
     */
    public boolean addToDB(String x, String y, String r, boolean hit, String author) {
        try {
            ResultEntity newEntity = new ResultEntity();
            newEntity.setX(x);
            newEntity.setY(y);
            newEntity.setR(r);
            newEntity.setHit(hit);
            newEntity.setAuthor(author);

            entityManager.persist(newEntity);
            return true;
        } catch (Exception e) {
            // Логирование ошибки (можно использовать Logger)
            System.err.println("Ошибка при добавлении записи в базу данных: " + e.getMessage());
            return false;
        }
    }

    /**
     * Получение всех результатов из базы данных.
     */
    public List<ResultEntity> getEntities() {
        try {
            Query query = entityManager.createQuery("SELECT entity FROM ResultEntity entity");
            return query.getResultList();
        } catch (Exception e) {
            // Логирование ошибки
            System.err.println("Ошибка при получении записей из базы данных: " + e.getMessage());
            return null;
        }
    }

    /**
     * Очистка результатов для указанного автора.
     */
    public void clear(String author) {
        try {
            Query query = entityManager.createQuery("DELETE FROM ResultEntity entity WHERE entity.author = :author");
            query.setParameter("author", author);
            query.executeUpdate();
        } catch (Exception e) {
            // Логирование ошибки
            System.err.println("Ошибка при очистке записей из базы данных: " + e.getMessage());
        }
    }
}