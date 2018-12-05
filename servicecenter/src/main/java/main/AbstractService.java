package main;

public abstract class AbstractService implements Service{
    public String serviceName() {
        return this.getClass().getName();
    }

    public abstract <T> T service(T t);
}
