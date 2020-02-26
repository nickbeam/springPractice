package ru.javawebinar.topjava.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private double timeStart;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
    private static StringBuilder stringBuilder = new StringBuilder();

    @Autowired
    private MealService service;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            String leftAlignFormat = "| %-12s | %-42s |";
            timeStart = System.currentTimeMillis();
            Calendar cal = Calendar.getInstance();
            stringBuilder.append(String.format(leftAlignFormat, dateFormat.format(cal.getTime()), description.getMethodName()));
        }

        protected void finished(Description description) {
            String rightAlignFormat = " %-15s |%n";
            double timeEnd = System.currentTimeMillis();
            double seconds = (timeEnd - timeStart) / 1000.0;
            stringBuilder.append(String.format(rightAlignFormat, new DecimalFormat("0.000").format(seconds)));
            System.out.println("+--------------+--------------------------------------------+-----------------+");
            System.out.println(stringBuilder.substring(stringBuilder.length() - 81, stringBuilder.length() - 1));
            System.out.println("+--------------+--------------------------------------------+-----------------+");
        }
    };

    @BeforeClass
    public static void tableHead() {
        stringBuilder.append("+--------------+--------------------------------------------+-----------------+\n");
        stringBuilder.append("|  Start time  |                   Test name                |  Duration (sec) |\n");
        stringBuilder.append("+--------------+--------------------------------------------+-----------------+\n");
    }

    @AfterClass
    public static void printTiming() {
        stringBuilder.append("+--------------+--------------------------------------------+-----------------+\n");
        System.out.print(stringBuilder);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void deleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=1");
        service.delete(1, USER_ID);
    }

    @Test
    public void deleteNotOwn() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + MEAL1_ID);
        service.delete(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = getCreated();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(newMeal, created);
        assertMatch(service.getAll(USER_ID), newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=1");
        service.get(1, USER_ID);
    }

    @Test
    public void getNotOwn() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + MEAL1_ID);
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + MEAL1_ID);
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }
}