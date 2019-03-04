import exception.DueDateCalculatorException;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class DueDateCalculatorTest {
    private DueDateCalculator dueDateCalculator;

    @Before
    public void setUp() {
        dueDateCalculator = new DueDateCalculator();
    }

    @Test(expected = DueDateCalculatorException.class)
    public void testInvalidSubmissionDay() {
        LocalDateTime someSunday = LocalDateTime.now().with(DayOfWeek.SUNDAY).with(LocalTime.of(12,0));
        dueDateCalculator.calculateDueDate(someSunday, null);
    }

    @Test(expected = DueDateCalculatorException.class)
    public void testSubmissionTimeEarly() {
        LocalDateTime tooEarlyTime = LocalDateTime.now().with(DayOfWeek.MONDAY).with(LocalTime.of(8,59));
        dueDateCalculator.calculateDueDate(tooEarlyTime, null);
    }

    @Test(expected = DueDateCalculatorException.class)
    public void testSubmissionTimeLate() {
        LocalDateTime lateTime = LocalDateTime.now().with(DayOfWeek.MONDAY).with(LocalTime.of(17,0));
        dueDateCalculator.calculateDueDate(lateTime, null);
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
        int turnaroundHours = 4*8;
        assertEquals(submittedAt.plusDays(4), dueDateCalculator.calculateDueDate(submittedAt, turnaroundHours));
    }

    @Test
    public void testDueOtherDay() {
        LocalDateTime submittedAt = LocalDateTime.now().with(DayOfWeek.WEDNESDAY).with(LocalTime.of(10, 5));
        int turnaroundHours = 8+3;
        assertEquals(submittedAt.plusHours(24+3), dueDateCalculator.calculateDueDate(submittedAt, turnaroundHours));
    }

    @Test
    public void testDueDateShiftsToNextDay() {
        LocalDateTime submittedAt = LocalDateTime.now().with(DayOfWeek.WEDNESDAY).with(LocalTime.of(15, 0));
        int turnaroundHours = 2;
        assertEquals(submittedAt.plusHours(16+2), dueDateCalculator.calculateDueDate(submittedAt, turnaroundHours));
    }

    @Test
    public void testDueDateShiftsToNextWeek() {
        LocalDateTime submittedAt = LocalDateTime.now().with(DayOfWeek.THURSDAY).with(LocalTime.of(9, 0));
        int turnaroundHours = 2*8;
        assertEquals(submittedAt.plusHours(4*24), dueDateCalculator.calculateDueDate(submittedAt, turnaroundHours));
    }
}