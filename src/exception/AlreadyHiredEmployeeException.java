package exception;

public class AlreadyHiredEmployeeException extends Exception {

    public AlreadyHiredEmployeeException() {
        super("<<system>> 하루 한번만 고용할 수 있습니다.");
    }
}
