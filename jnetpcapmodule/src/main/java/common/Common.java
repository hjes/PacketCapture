package common;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Common {
    public static Executor executor = Executors.newCachedThreadPool();
    public static final String PACKET_LOSE_EVENT = "packet_loss_event";


    public static final class CaptureThreadState{
        public static final int RUNNING = 0;
        public static final int PAUSE = 1;
        public static final int CONSUME = 2;
        public static final int QUIT = 3;
        public static final int WAITING = 4;
    }
}
