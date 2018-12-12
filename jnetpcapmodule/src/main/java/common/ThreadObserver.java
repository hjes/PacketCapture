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
    }

    public void notifyNow(){
        while (!isWait){
            System.err.println("waiting");
        }
        synchronized (this){
            this.notify();
        }
    }
}
