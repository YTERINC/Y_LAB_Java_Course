package ru.yterinc.services;

import ru.yterinc.models.User;

import java.util.HashMap;
import java.util.Map;

// Бизнес-логика для пользователя
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public boolean register(String name, String password, String email) {
        if (users.containsKey(email)) {
            return false; // Email уже существует
        }
        users.put(email, new User(name, password, email));
        return true;
    }

    public User login(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null; // Неверные данные
    }

    public boolean updateUser(User user, String newName, String newPassword, String newEmail) {
        if (users.containsKey(newEmail)) {
            return false;
        }
        String oldName = user.getName();
        user.setName(newName);
        user.setPassword(newPassword);
        user.setEmail(newEmail);
        users.put(newEmail, user);
        users.remove(oldName);
        return true;
    }

    public void deleteUser(String email) {
        users.remove(email);
    }

    public User getUser(String email) {
        if (!users.containsKey(email)) {
            return null;
        }
        return users.get(email);
    }

    public void resetPassword(String email) {
        User user = users.get(email);
        user.setPassword("12345");
        System.out.println("<------------------------------------------------>");
        System.out.println("Отправка письма по электронной почте о сбросе --->");
        System.out.println("Пароль сброшен на значение < 12345 >");
        System.out.println("<------------------------------------------------>");
    }
}
