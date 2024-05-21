package exception;

public class NotFoundBeanException extends NotFoundException {

    public NotFoundBeanException() {
        super("<<system>> 해당 원두가 없습니다!");
    }
}
