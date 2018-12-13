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
        while (!isWait){
            System.err.println("waiting for notify");
        }
        synchronized (this){
            System.out.println("notify");
            this.notify();
        }
    }
}
