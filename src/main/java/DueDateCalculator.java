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
    private final static LocalTime WORK_DAY_START = LocalTime.of(9, 0);
    private final static LocalTime WORK_DAY_END = LocalTime.of(17, 0);

    private LocalDateTime addDaysToDueDate(LocalDateTime dueDate, Integer daysToAdd) {
        if(WORKDAYS.indexOf(dueDate.getDayOfWeek()) + daysToAdd >= WORKDAYS.size()) {
            dueDate = dueDate.plusDays(2);
        }
        return dueDate.plusDays(daysToAdd);
    }

    private LocalDateTime addHoursToDueDate(LocalDateTime dueDate, Integer hoursToAdd) {
        dueDate = dueDate.plusHours(hoursToAdd);
        if(dueDate.toLocalTime().isAfter(WORK_DAY_END)) {
            dueDate = dueDate.plusHours(16);
        }
        if(!WORKDAYS.contains(dueDate.getDayOfWeek())) {
            dueDate = dueDate.plusDays(2);
        }
        return dueDate;
    }

    public LocalDateTime calculateDueDate(LocalDateTime submittedAt, Integer turnaroundHours) {
        if(!WORKDAYS.contains(submittedAt.getDayOfWeek())) {
            throw new DueDateCalculatorException("Invalid day - problems must be submitted on workdays.");
        }
        if(submittedAt.toLocalTime().isBefore(WORK_DAY_START) || submittedAt.toLocalTime().isAfter(WORK_DAY_END)) {
            throw new DueDateCalculatorException("Invalid time - problems must be submitted between 9AM - 5PM");
        }

        Integer totalDays = turnaroundHours / 8;
        Integer weeksToAdd = totalDays / 5;
        Integer daysToAdd = totalDays % 5;
        Integer hoursToAdd = turnaroundHours % 8;

        LocalDateTime dueDate = submittedAt.plusWeeks(weeksToAdd);
        dueDate = addDaysToDueDate(dueDate, daysToAdd);
        dueDate = addHoursToDueDate(dueDate, hoursToAdd);
        return dueDate;
    }
}
