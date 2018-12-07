package common;


public interface Service<T> {
    String serviceName();
    T service();
}
