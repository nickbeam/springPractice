package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        printMealList(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000));
        printMealList(getFilteredWithExceededByStream(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000));
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDate = new HashMap<>();
        for (UserMeal meal:mealList) {
            caloriesPerDate.merge(meal.getDate(), meal.getCalories(), Integer::sum);
        }
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        for (UserMeal meal:mealList) {
            if (TimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                userMealWithExceeds.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesPerDate.get(meal.getDate()) > caloriesPerDay));
            }
        }
        return userMealWithExceeds;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededByStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDate = mealList.stream().collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));
                //.collect(Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, Integer::sum));
        return mealList.stream()
                .filter(m -> TimeUtil.isBetween(m.getTime(), startTime, endTime))
                .map(m -> new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), caloriesPerDate.get(m.getDate()) > caloriesPerDay)).collect(Collectors.toList());
    }

    private static void printMealList(List<UserMealWithExceed> userMealWithExceeds) {
        userMealWithExceeds.forEach(System.out::println);
    }
}
