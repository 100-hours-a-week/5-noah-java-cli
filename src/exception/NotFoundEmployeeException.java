package exception;

public class NotFoundEmployeeException extends NotFoundException {

    public NotFoundEmployeeException() {
        super("해당 직원이 없습니다!");
    }
}
