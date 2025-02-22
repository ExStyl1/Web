package com.example.demo.EJBbeans;

import com.example.demo.entities.UserEntity;
import com.example.demo.util.Hex;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Stateless
public class UserEJB {

    private static final Logger LOGGER = Logger.getLogger(UserEJB.class.getName());

    @PersistenceContext(unitName = "examplePU")
    private EntityManager entityManager;

    /**
     * Добавление нового пользователя в базу данных.
     */
    public boolean addUserToDB(String login, String password) {
        if (checkLoginInDB(login)) {
            LOGGER.warning("Пользователь с логином '" + login + "' уже существует");
            return false;
        }

        try {
            UserEntity user = new UserEntity();
            user.setLogin(login);

            byte[] hash = hashString(System.getenv("PAPER") + password + "S14D!#!af^*F");
            if (hash == null) {
                LOGGER.severe("Ошибка при хэшировании пароля для логина: " + login);
                return false;
            }

            user.setPassword(Hex.bytesToHex(hash));
            user.setToken(UUID.randomUUID().toString());

            entityManager.persist(user);
            LOGGER.info("Пользователь успешно добавлен: " + login);
            return true;
        } catch (Exception e) {
            LOGGER.severe("Ошибка при добавлении пользователя: " + e.getMessage());
            return false;
        }
    }

    /**
     * Проверка существования пользователя в базе данных.
     */
    public boolean checkUserInDB(String login, String password) {
        try {
            UserEntity user = entityManager.find(UserEntity.class, login);
            if (user == null) {
                LOGGER.warning("Пользователь с логином '" + login + "' не найден");
                return false;
            }

            byte[] hash = hashString(System.getenv("PAPER") + password + "S14D!#!af^*F");
            if (hash == null) {
                LOGGER.severe("Ошибка при хэшировании пароля для логина: " + login);
                return false;
            }

            boolean isValid = user.getPassword().equals(Hex.bytesToHex(hash));
            if (!isValid) {
                LOGGER.warning("Неверный пароль для логина: " + login);
            }

            return isValid;
        } catch (Exception e) {
            LOGGER.severe("Ошибка при проверке пользователя: " + e.getMessage());
            return false;
        }
    }

    /**
     * Получение токена по логину.
     */
    public String getAssociatedToken(String login) {
        try {
            UserEntity user = entityManager.find(UserEntity.class, login);
            if (user == null) {
                LOGGER.warning("Пользователь с логином '" + login + "' не найден");
                return null;
            }

            LOGGER.info("Токен успешно получен для логина: " + login);
            return user.getToken();
        } catch (Exception e) {
            LOGGER.severe("Ошибка при получении токена: " + e.getMessage());
            return null;
        }
    }

    /**
     * Получение логина по токену.
     */
    public String getAssociatedLogin(String token) {
        try {
            Query query = entityManager.createQuery("SELECT u FROM UserEntity u WHERE u.token = :token", UserEntity.class);
            query.setParameter("token", token);

            List<UserEntity> users = query.getResultList();
            if (users.isEmpty()) {
                LOGGER.warning("Пользователь с токеном '" + token + "' не найден");
                return null;
            }

            UserEntity user = users.get(0);
            LOGGER.info("Логин успешно получен для токена: " + token);
            return user.getLogin();
        } catch (Exception e) {
            LOGGER.severe("Ошибка при получении логина по токену: " + e.getMessage());
            return null;
        }
    }

    /**
     * Проверка существования логина в базе данных.
     */
    private boolean checkLoginInDB(String login) {
        try {
            UserEntity user = entityManager.find(UserEntity.class, login);
            return user != null;
        } catch (Exception e) {
            LOGGER.severe("Ошибка при проверке существования логина: " + e.getMessage());
            return false;
        }
    }

    /**
     * Хэширование строки с использованием SHA-256.
     */
    private byte[] hashString(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(s.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.severe("Алгоритм хэширования недоступен: " + e.getMessage());
            return null;
        }
    }
}