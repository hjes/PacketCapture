package exceptions;

public class NotRegisterException extends RuntimeException{
    private String message;
    public NotRegisterException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                message;
    }
}
