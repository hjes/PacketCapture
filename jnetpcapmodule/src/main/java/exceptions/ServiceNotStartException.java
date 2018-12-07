package exceptions;

public class ServiceNotStartException extends RuntimeException {
    private String message;
    public ServiceNotStartException(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                message;
    }
}
