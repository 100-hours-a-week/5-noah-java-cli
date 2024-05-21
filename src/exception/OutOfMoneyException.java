package exception;

public class OutOfMoneyException extends Exception {

    public OutOfMoneyException() {
        super("<<system>> 원두를 구매할 돈이 없습니다!");
    }
}
