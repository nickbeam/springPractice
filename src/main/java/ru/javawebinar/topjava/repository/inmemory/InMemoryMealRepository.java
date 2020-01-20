package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.w3c.dom.ls.LSOutput;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Map<Integer, Meal>> repository = new HashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> {save(meal, 2);});
        MealsUtil.MEALSADM.forEach(meal -> {save(meal, 1);});
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save meal {} to userId {}", meal, userId);
        repository.putIfAbsent(userId, new HashMap<>());
        Map<Integer, Meal> meals = getUserMeals(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete meal {} from userId {}", id, userId);
        Map<Integer, Meal> meals = getUserMeals(userId);
        return meals.remove(id) != null && repository.put(userId, meals) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal {} from userId {}", id, userId);
        return getUserMeals(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll meals from userId {}", userId);
        return getUserMeals(userId).values().stream().sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());
    }

    private Map<Integer, Meal> getUserMeals(int userId) {
        return repository.get(userId);
    }

    @Override
    public Collection<Meal> getBetween(LocalDate startDate, LocalDate endDate, int userId) {
        return getAll(userId).stream()
                .filter(m -> DateTimeUtil.isBetweenDate(m.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

