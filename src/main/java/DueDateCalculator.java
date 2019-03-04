import exception.DueDateCalculatorException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class DueDateCalculator {
    private final static List<DayOfWeek> WORKDAYS = Arrays.asList(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
    );
    private final static LocalTime WORKDAY_START = LocalTime.of(9, 0);
    private final static LocalTime WORKDAY_END = LocalTime.of(16, 59);
    private final static Integer WORKWEEK_IN_DAYS = 5;
    private final static Integer WEEKEND_IN_DAYS = 2;
    private final static Integer WORKDAY_IN_HOURS = 8;
    private final static Integer HOURS_BETWEEN_WORKDAYS = 16;

    private LocalDateTime addDaysToDueDate(LocalDateTime dueDate, Integer daysToAdd) {
        if(WORKDAYS.indexOf(dueDate.getDayOfWeek()) + daysToAdd >= WORKDAYS.size()) {
            dueDate = dueDate.plusDays(WEEKEND_IN_DAYS);
        }
        return dueDate.plusDays(daysToAdd);
    }

    private LocalDateTime addHoursToDueDate(LocalDateTime dueDate, Integer hoursToAdd) {
        dueDate = dueDate.plusHours(hoursToAdd);
        if(dueDate.toLocalTime().isAfter(WORKDAY_END)) {
            dueDate = dueDate.plusHours(HOURS_BETWEEN_WORKDAYS);
        }
        if(!WORKDAYS.contains(dueDate.getDayOfWeek())) {
            dueDate = dueDate.plusDays(WEEKEND_IN_DAYS);
        }
        return dueDate;
    }

    public LocalDateTime calculateDueDate(LocalDateTime submittedAt, Integer turnaroundHours) {
        if(!WORKDAYS.contains(submittedAt.getDayOfWeek())) {
            throw new DueDateCalculatorException("Invalid day - problems must be submitted on workdays.");
        }
        if(submittedAt.toLocalTime().isBefore(WORKDAY_START) || submittedAt.toLocalTime().isAfter(WORKDAY_END)) {
            throw new DueDateCalculatorException("Invalid time - problems must be submitted between 9AM - 5PM");
        }

        Integer totalDays = turnaroundHours / WORKDAY_IN_HOURS;
        Integer weeksToAdd = totalDays / WORKWEEK_IN_DAYS;
        Integer daysToAdd = totalDays % WORKWEEK_IN_DAYS;
        Integer hoursToAdd = turnaroundHours % WORKDAY_IN_HOURS;

        LocalDateTime dueDate = submittedAt.plusWeeks(weeksToAdd);
        dueDate = addDaysToDueDate(dueDate, daysToAdd);
        dueDate = addHoursToDueDate(dueDate, hoursToAdd);
        return dueDate;
    }
}
