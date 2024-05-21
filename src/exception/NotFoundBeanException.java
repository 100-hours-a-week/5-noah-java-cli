package exception;

public class NotFoundBeanException extends Exception {

    public NotFoundBeanException() {
        super("<<system>> 해당 원두가 없습니다!");
    }
}
