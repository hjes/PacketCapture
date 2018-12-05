package test;

import main.AbstractService;
import org.junit.Test;

public class TestCenter {

    @Test
    public void test(){
        AbstractService abstractService = new TestClass();
        System.out.println(abstractService.getClass().getSimpleName() + " - -" +
                abstractService.getClass().getName() + " - -" +
                abstractService.getClass().getCanonicalName());
    }

}
