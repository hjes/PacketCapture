package packet.repository;

public interface Callback<T> {
    void callback(T t);
}
