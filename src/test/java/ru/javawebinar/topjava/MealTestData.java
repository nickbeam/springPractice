package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public final static Meal MEAL1 = new Meal(100002, LocalDateTime.of(2015, 5, 30, 10, 0, 0), "Завтрак", 500);
    public final static Meal MEAL2 = new Meal(100003, LocalDateTime.of(2015, 5, 30, 13, 0, 0), "Обед", 1000);
    public final static Meal MEAL3 = new Meal(100004, LocalDateTime.of(2015, 5, 30, 20, 0, 0), "Ужин", 500);

    public final static Meal MEAL4 = new Meal(100005, LocalDateTime.of(2015, 5, 31, 10, 0, 0), "Завтрак", 1000);
    public final static Meal MEAL5 = new Meal(100006, LocalDateTime.of(2015, 5, 31, 13, 0, 0), "Обед", 500);
    public final static Meal MEAL6 = new Meal(100007, LocalDateTime.of(2015, 5, 31, 20, 0, 0), "Ужин", 510);

    public final static Meal ADMIN_MEAL1 = new Meal(100008, LocalDateTime.of(2015, 6, 1, 14, 0, 0), "Админ ланч", 510);
    public final static Meal ADMIN_MEAL2 = new Meal(100009, LocalDateTime.of(2015, 6, 1, 21, 0, 0), "Админ ужин", 1500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "userId");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("userId").isEqualTo(expected);
    }
}
