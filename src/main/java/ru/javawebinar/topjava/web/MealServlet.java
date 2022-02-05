package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        List<UserMeal> mealsTo = new ArrayList<>();
        mealsTo.add(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "soup", 300));
        mealsTo.add(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 12, 0), "salad", 100));
        mealsTo.add(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "garlic", 20));
        mealsTo.add(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 15, 0), "potatoes", 100));
        mealsTo.add(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 17, 0), "chicken", 700));
        mealsTo.add(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 19, 0), "grill meat", 800));
        List<UserMealWithExcess> mealsToEx = UserMealsUtil
                .filteredByStreams(mealsTo,
                        LocalTime.of(7, 0),
                        LocalTime.of(18, 0),
                        2000);

        log.debug(mealsToEx.size()+"");
        req.setAttribute("meals", mealsToEx);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
