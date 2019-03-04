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
        LocalDateTime someSunday = LocalDateTime.now().with(DayOfWeek.MONDAY).with(LocalTime.of(8,59));
        dueDateCalculator.calculateDueDate(someSunday, null);
    }
}