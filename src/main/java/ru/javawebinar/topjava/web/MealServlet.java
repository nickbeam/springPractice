package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.Config;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        int id = request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id"));

        switch (action) {
            case "edit":
                Meal meal = repository.get(id);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("meal.jsp").forward(request, response);
                break;
            case "delete":
                repository.delete(id);
                break;
            default:

        }
        request.setAttribute("meals", MealsUtil.getFiltered(repository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        request.getRequestDispatcher("mealList.jsp").forward(request, response);
//        response.sendRedirect("mealList.jsp");
    }
}
