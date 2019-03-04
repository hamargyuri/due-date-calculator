import exception.DueDateCalculatorException;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class DueDateCalculatorTest {
    private final static Integer WEEKEND_IN_HOURS = 48;
    private final static Integer WORKDAY_IN_HOURS = 8;
    private final static Integer HOURS_BETWEEN_WORKDAYS = 16;

    private DueDateCalculator dueDateCalculator;

    @Before
    public void setUp() {
        dueDateCalculator = new DueDateCalculator();
    }

    @Test(expected = DueDateCalculatorException.class)
    public void testInvalidSubmissionDay() {
        LocalDateTime someSunday = LocalDateTime.now().with(DayOfWeek.SUNDAY).with(LocalTime.of(12,0));
        dueDateCalculator.calculateDueDate(someSunday, 0);
    }

    @Test(expected = DueDateCalculatorException.class)
    public void testSubmissionTimeEarly() {
        LocalDateTime tooEarlyTime = LocalDateTime.now().with(DayOfWeek.MONDAY).with(LocalTime.of(8,59));
        dueDateCalculator.calculateDueDate(tooEarlyTime, 0);
    }

    @Test(expected = DueDateCalculatorException.class)
    public void testSubmissionTimeLate() {
        LocalDateTime lateTime = LocalDateTime.now().with(DayOfWeek.MONDAY).with(LocalTime.of(17,0));
        dueDateCalculator.calculateDueDate(lateTime, 0);
    }

    @Test
    public void testDueOnSameDay() {
        LocalDateTime submittedAt = LocalDateTime.now().with(DayOfWeek.TUESDAY).with(LocalTime.of(9, 0));
        int turnaroundHours = 4;
        assertEquals(submittedAt.plusHours(turnaroundHours),
                dueDateCalculator.calculateDueDate(submittedAt, turnaroundHours));
    }

    @Test
    public void testDueSameWeek() {
        LocalDateTime submittedAt = LocalDateTime.now().with(DayOfWeek.MONDAY).with(LocalTime.of(9, 0));
        int turnaroundHours = 4 * WORKDAY_IN_HOURS;
        assertEquals(submittedAt.plusDays(4), dueDateCalculator.calculateDueDate(submittedAt, turnaroundHours));
    }

    @Test
    public void testDueDateJustShiftsToNextDay() {
        LocalDateTime submittedAt = LocalDateTime.now().with(DayOfWeek.THURSDAY).with(LocalTime.of(15, 0));
        int turnaroundHours = 2;
        assertEquals(submittedAt.plusHours(2 + HOURS_BETWEEN_WORKDAYS),
                dueDateCalculator.calculateDueDate(submittedAt, turnaroundHours));
    }

    @Test
    public void testDueDateJustShiftsToNextWeek() {
        LocalDateTime submittedAt = LocalDateTime.now().with(DayOfWeek.WEDNESDAY).with(LocalTime.of(9, 0));
        int turnaroundHours = 3 * WORKDAY_IN_HOURS;
        assertEquals(submittedAt.plusHours(3*24 + WEEKEND_IN_HOURS),
                dueDateCalculator.calculateDueDate(submittedAt, turnaroundHours));
    }

    @Test
    public void testDueDateShiftsToNextWeekWithLessThanEightHours() {
        LocalDateTime submittedAt = LocalDateTime.now().with(DayOfWeek.FRIDAY).with(LocalTime.of(16, 59));
        int turnaroundHours = 7;
        assertEquals(submittedAt.plusHours(HOURS_BETWEEN_WORKDAYS + 7 + WEEKEND_IN_HOURS),
                dueDateCalculator.calculateDueDate(submittedAt, turnaroundHours));
    }
}