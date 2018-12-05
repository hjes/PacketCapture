package main;


public interface Service {
    String serviceName();
    <T> T service(T t);
}
