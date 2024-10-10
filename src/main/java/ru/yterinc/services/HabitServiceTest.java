package ru.yterinc.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yterinc.models.User;


import static org.assertj.core.api.Assertions.assertThat;

public class HabitServiceTest {
    private HabitService habitService;
    private User user;

    @BeforeEach
    public void setUp() {
        habitService = new HabitService();
        user = new User("Alice", "password123", "alice@example.com");
        habitService.addHabit(user, "Jogging", "Morning jogging", "Daily"); // предположим, метод существует
    }

    @Test
    public void testUpdateHabit() {
        assertThat(habitService.updateHabit(user, "Jogging", "Walking", "Evening walk", "Weekly")).isTrue();
        assertThat(habitService.checkPresentHabit(user, "Walking")).isTrue();
    }

    @Test
    public void testUpdateHabitNotExists() {
        assertThat(habitService.updateHabit(user, "NonExistingHabit", "New Name", "New Description", "New Frequency")).isFalse();
    }

    @Test
    public void testCheckPresentHabit() {
        assertThat(habitService.checkPresentHabit(user, "Jogging")).isTrue();
        assertThat(habitService.checkPresentHabit(user, "NonExistingHabit")).isFalse();
    }
}
