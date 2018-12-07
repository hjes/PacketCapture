package common;

public abstract class AbstractService implements Service{
    public String serviceName() {
        return this.getClass().getName();
    }
}
