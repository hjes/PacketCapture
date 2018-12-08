package common;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Common {
    public static Executor executor = Executors.newCachedThreadPool();
    public static final String PACKET_LOSE_EVENT = "packet_loss_event";
}
