package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Config;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = Config.getInstance().getRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = repository.save(new Meal(id.isEmpty() ? null : Integer.parseInt(id), LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))));
        log.debug(id.isEmpty() ? "Create {}" : "Update {}", meal);
        request.setAttribute("meals", MealsUtil.getFiltered(repository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        request.getRequestDispatcher("mealList.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "create":
            case "update":
                Meal meal = "create".equals(action) ? new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 500) : repository.get(getId(request));
                log.debug(action.equals("create") ? "Create {}" : "Update {}", meal);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("meal.jsp").forward(request, response);
                break;
            case "delete":
                int id = getId(request);
                log.debug("Delete {}", repository.get(id));
                repository.delete(id);
                break;
            case "all":
            default:

        }
        request.setAttribute("meals", MealsUtil.getFiltered(repository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        request.getRequestDispatcher("mealList.jsp").forward(request, response);
    }

    private int getId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }
}
