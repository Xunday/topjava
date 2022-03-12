package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {

    public static final List<UserMeal> MEAL_LIST = Arrays.asList(
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "soup", 300),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 12, 0), "salad", 100),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "garlic", 20),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 15, 0), "potatoes", 100),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 17, 0), "chicken", 700),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 19, 0), "grill meat", 800));

    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> getMealsWithExcess (List<UserMeal> meals, int caliriesPerDay) {
        return filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, caliriesPerDay);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        Map<LocalDateTime, Integer> calloriesPerDate = new HashMap<>();
        for (UserMeal userMeal : meals
        ) {
            LocalDateTime dateTime = userMeal.getDateTime();
            calloriesPerDate.put(dateTime, calloriesPerDate.getOrDefault(dateTime, 0) + userMeal.getCalories());
        }
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        for (UserMeal userMeal : meals
        ) {
            LocalDateTime dateTime = userMeal.getDateTime();
            if (TimeUtil.isBetweenHalfOpen(dateTime.toLocalTime(), startTime, endTime)) {
                userMealWithExcessList
                        .add(new UserMealWithExcess(userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                calloriesPerDate.getOrDefault(dateTime, 0) > caloriesPerDay));
            }
        }
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        Map<LocalDateTime, Integer> calloriesDate = meals.stream()
                .collect(
                        Collectors.groupingBy(UserMeal::getDateTime, Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> createTo(meal, calloriesDate.get(meal.getDateTime()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static UserMealWithExcess createTo(UserMeal userMeal, Boolean excees) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excees);
    }
}
