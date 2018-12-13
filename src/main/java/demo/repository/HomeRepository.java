package demo.repository;

import demo.mvc.AbstractRepository;

import java.util.Timer;
import java.util.TimerTask;

public class HomeRepository extends AbstractRepository {

    private Timer timer;

    public void setAutoFlashPeriodTime(long timeInMills,long periodTime,Callback<Object> callback){
        if (timer==null)
            timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                callback.callback(null);
            }
        },timeInMills,periodTime);
    }

    public void pauseAutoFlash(){
        timer.cancel();
        timer = null;
    }
}
