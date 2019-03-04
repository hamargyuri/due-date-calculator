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
}