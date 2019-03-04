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

    public LocalDateTime calculateDueDate(LocalDateTime submittedAt, Integer turnaroundHours) {
        if(!WORKDAYS.contains(submittedAt.getDayOfWeek())) {
            throw new DueDateCalculatorException("Invalid day - problems must be submitted on workdays.");
        }
        return null;
    }
}
