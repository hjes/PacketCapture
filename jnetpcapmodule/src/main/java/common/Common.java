package common;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Common {
    public static Executor executor = Executors.newCachedThreadPool();
}
