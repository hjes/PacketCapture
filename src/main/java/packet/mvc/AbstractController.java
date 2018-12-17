package packet.mvc;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 1、初始化
 * 2、
 */
public abstract class AbstractController<T extends AbstractRepository> {

    public T repository;

    @PostConstruct
    private void abstractInit(){
        repository = initRepository();
        init();
    }

    public abstract void init();

    @PreDestroy
    public abstract void destroy();

    public abstract T initRepository();
}
