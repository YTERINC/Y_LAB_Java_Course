package ru.yterinc.services;
import ru.yterinc.models.Habit;
import ru.yterinc.models.User;

import java.util.*;

public class HabitService {
    private final Map<User, List<Habit>> userHabits = new HashMap<>();

    public void addHabit(User user, String name, String description, String frequency) {
        Habit habit = new Habit(user, name, description, frequency);
        userHabits.computeIfAbsent(user, k -> new ArrayList<>()).add(habit); // если такого user нет, то добавляем список
        // и к нему добавляем привычку
    }

    public void markComplete(User user, String habitName) {
        List<Habit> habits = userHabits.get(user);
        if (habits != null) {
            for (Habit habit : habits) {
                if (habit.getName().equals(habitName)) {
                    habit.getCompletionDates().add(new Date());
                }
            }
        }
    }

    public List<Habit> getHabits(User user) {
        return userHabits.getOrDefault(user, new ArrayList<>());  // если нет такого email, то возвращаем пустой лист
    }

    public boolean deleteHabit(User user, String habitName) {
        List<Habit> habits = userHabits.get(user);
        if (habits != null) {
            return habits.removeIf(habit -> habit.getName().equals(habitName));
        }
        return false;
    }

    public void deleteAllHabits(User user) {
        if (user != null) {
            userHabits.remove(user);
        }
    }

    public boolean updateHabit(User user, String habitNameChg, String newName,
                               String newDescription, String newFrequency) {
        Optional<Habit> habitChg = userHabits.get(user).stream().filter(h -> h.getName().equals(habitNameChg)) // фильтрация по имени
                .findFirst();
        if (!habitChg.isPresent()) {
            return false;
        }
        habitChg.get().setName(newName);
        habitChg.get().setDescription(newDescription);
        habitChg.get().setFrequency(newFrequency);
        return true;
    }

    public boolean checkPresentHabit(User user, String habitNameChg) {
        return userHabits.get(user).stream().anyMatch(h -> h.getName().equals(habitNameChg));
    }
}
