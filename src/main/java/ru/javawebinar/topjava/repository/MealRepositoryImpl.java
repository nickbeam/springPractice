package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealRepositoryImpl implements MealRepository {
    public Map<Integer, Meal> repository = new ConcurrentHashMap<>();

    @Override
    public void create(Meal meal) {
        repository.put(meal.getId(), meal);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public void update(Meal meal, int id) {
        repository.put(id, meal);
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }
}
