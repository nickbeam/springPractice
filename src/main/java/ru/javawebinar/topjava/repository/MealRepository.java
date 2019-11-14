package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    void create(Meal meal);

    Meal get(String id);

    List<Meal> getAll();

    void update(Meal meal, String id);

    void delete(String id);
}
