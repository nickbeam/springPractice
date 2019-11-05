package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    void create(Meal meal);

    Meal get(int id);

    List<Meal> getAll();

    void update(Meal meal, int id);

    void delete(int id);
}
