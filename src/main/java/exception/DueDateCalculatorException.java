package exception;

public class DueDateCalculatorException extends RuntimeException {
    public DueDateCalculatorException() {
        super();
    }

    public DueDateCalculatorException(String message) {
        super(message);
    }
}
