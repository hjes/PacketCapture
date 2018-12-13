package common;

public class ThreadObserver {
    private volatile boolean isWait = false;
    public void waitNow(){
        while(!isWait){
            synchronized (this){
                isWait = true;
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        isWait = false;
    }

    public void notifyNow(){
        if (!isWait)
            System.out.println("has not capture a packet, so can't notify");
        synchronized (this){
            this.notify();
        }
    }
}
