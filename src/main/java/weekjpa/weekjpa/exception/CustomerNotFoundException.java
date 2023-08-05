package weekjpa.weekjpa.exception;

public class CustomerNotFoundException extends RuntimeException {

    private static final String MESSAGE = "조회를 요청한 손님을 찾을 수 없습니다.";

    public CustomerNotFoundException() {
        super(MESSAGE);
    }
}
