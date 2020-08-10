package ru.javawebinar.topjava.web;

import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.UserTestData.*;

class RootControllerTest extends AbstractControllerTest {

    @Autowired
    private MealService service;

    @Test
    public void getUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users",
                        new AssertionMatcher<List<User>>() {
                            @Override
                            public void assertion(List<User> actual) throws AssertionError {
                                assertMatch(actual, ADMIN, USER);
                            }
                        }
                ));
    }

    @Test
    public void getMeals() throws Exception {
        mockMvc.perform(get("/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
                .andExpect(model().attribute("meals",
                        new AssertionMatcher<List<MealTo>>() {
                            @Override
                            public void assertion(List<MealTo> actual) throws AssertionError {
                                MealTestData.assertMatch(MealsUtil.toToMeal(actual), MealTestData.MEALS);
                            }
                        }
                ));
    }
}