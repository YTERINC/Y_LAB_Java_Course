package ru.yterinc;
import ru.yterinc.models.Habit;
import ru.yterinc.models.User;
import ru.yterinc.services.HabitService;
import ru.yterinc.services.UserService;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final UserService userService = new UserService();
    private static final HabitService habitService = new HabitService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите команду: register, login, exit");
            String command = scanner.nextLine();

            switch (command) {
                case "register": {
                    System.out.println("Name: ");
                    String name = scanner.nextLine();
                    System.out.println("Password: ");
                    String password = scanner.nextLine();
                    System.out.println("Email: ");
                    String email = scanner.nextLine();

                    if (userService.register(name, password, email)) {
                        System.out.println("Регистрация успешно выполнена!");
                    } else {
                        System.out.println("Пользователь с таким email уже существует!");
                    }
                    break;
                }
                case "login": {
                    System.out.println("Email: ");
                    String email = scanner.nextLine();
                    System.out.println("Password: ");
                    String password = scanner.nextLine();

                    User user = userService.login(email, password);
                    if (user != null) {
                        System.out.println("Вход выполнен успешно");

                        while (true) {
                            System.out.println("Команды для управления привычками: add, complete, list, update, delete");
                            System.out.println("Команды для управления пользователем: updUser, delUser, resPas, logout");
                            String habitCommand = scanner.nextLine();

                            switch (habitCommand) {
                                case "add": {
                                    System.out.println("Название привычки: ");
                                    String nameHabit = scanner.nextLine();
                                    System.out.println("Описание: ");
                                    String description = scanner.nextLine();
                                    System.out.println("Частота (daily/weekly): ");
                                    String frequency = scanner.nextLine();
                                    habitService.addHabit(user, nameHabit, description, frequency);
                                    System.out.println("Привычка добавлена!");
                                    break;
                                }
                                case "complete": {
                                    System.out.println("Введите название привычки для отметки: ");
                                    String habitName = scanner.nextLine();
                                    habitService.markComplete(user, habitName);
                                    System.out.println("Привычка отмечена как выполненная!");
                                    break;
                                }
                                case "list": {
                                    List<Habit> habits = habitService.getHabits(user);
                                    System.out.println("Список ваших привычек:");
                                    habits.stream().map(Habit::getName).forEach(System.out::println);
                                    System.out.println();
                                    System.out.println("*********************");
                                    break;
                                }

                                case "update": {
                                    System.out.println("Введите название привычки, которую хотите изменить: ");
                                    String habitNameChg = scanner.nextLine();
                                    if (!habitService.checkPresentHabit(user, habitNameChg)) {
                                        System.out.println("Такой привычки не существует!");
                                        break;
                                    }
                                    System.out.println("Введите новые данные привычки: ");
                                    System.out.println("Новое название привычки: ");
                                    String newNameHabit = scanner.nextLine();
                                    System.out.println("Новое описание: ");
                                    String newDescription = scanner.nextLine();
                                    System.out.println("Новая частота (daily/weekly): ");
                                    String newFrequency = scanner.nextLine();
                                    habitService.updateHabit(user, habitNameChg, newNameHabit, newDescription, newFrequency);
                                    break;
                                }

                                case "delete": {
                                    System.out.println("Введите название привычки для ее удаления: ");
                                    String habitName = scanner.nextLine();
                                    if (habitService.deleteHabit(user, habitName)) {
                                        System.out.println("Привычка удалена!");
                                    } else {
                                        System.out.println("Привычка не найдена!");
                                    }
                                    break;
                                }
                                case "logout": {
                                    System.out.println("Вы вышли из учетной записи пользователя");
                                    break;
                                }
                                case "delUser": {
                                    System.out.println("***** Выход и удаление учетной записи *****");
                                    break;
                                }

                                case "updUser": {
                                    System.out.println("Введите новые данные пользователя");
                                    System.out.println("Новое имя: ");
                                    String newName = scanner.nextLine();
                                    System.out.println("Новый пароль: ");
                                    String newPassword = scanner.nextLine();
                                    System.out.println("Новый email: ");
                                    String newEmail = scanner.nextLine();
                                    if (userService.updateUser(user, newName, newPassword, newEmail)) {
                                        System.out.println("Информация о пользователе обновлена");
                                        System.out.println("Имя:  " + user.getName());
                                        System.out.println("Пароль:  " + user.getPassword());
                                        System.out.println("Email:  " + user.getEmail());
                                    } else {
                                        System.out.println("Пользователь с таким email уже существует!");
                                    }
                                    break;
                                }

                                case "resPas": {
                                    userService.resetPassword(email);
                                    break;
                                }

                                default: {
                                    System.out.println("Неверная команда");
                                }
                            }

                            if (habitCommand.equals("logout")) {
                                break;
                            } else if (habitCommand.equals("delUser")) {
                                habitService.deleteAllHabits(userService.getUser(email));
                                userService.deleteUser(email);
                                break;
                            }
                        }

                    } else {
                        System.out.println("Неверный email или пароль");
                    }
                    break;
                }
                case "exit": {
                    System.out.println("***** Выход *****");
                    return;
                }
                default: {
                    System.out.println("Неверная команда!!!");
                }
            }
        }
    }
}

