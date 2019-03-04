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
}