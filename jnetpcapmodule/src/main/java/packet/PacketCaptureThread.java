package packet;

import common.Common;
import common.ObserverCenter;
import packet.processor.PacketProcessor;

/**
 * 抓包线程
 */
public class PacketCaptureThread extends Thread{
    //抓包的接口名
    private String interfaceName;
    //Handler
    private PacketHandler<String> packetHandler;
    private boolean hasPause = false;

    public int currentState = Common.CaptureThreadState.WAITING;

    public PacketCaptureThread(String interfaceName){
        this.interfaceName = interfaceName;
        packetHandler = new PacketHandler<>(interfaceName);
    }

    @Override
    public void run() {
        PacketCapture.mainCapture(interfaceName,packetHandler);
    }

    public void pause(){
        if (!hasPause) {
            packetHandler.setPause();
            hasPause = true;
            ObserverCenter.notifyLogging(interfaceName + "--pause");
            currentState = Common.CaptureThreadState.PAUSE;
        }
    }

    public void consume(){
        if (hasPause) {
            packetHandler.setConsume();
            ObserverCenter.notifyLogging(interfaceName + "--consume");
            currentState = Common.CaptureThreadState.CONSUME;
        }
        hasPause = false;
    }

    public void startService(){
        super.start();
        currentState = Common.CaptureThreadState.CONSUME;
    }

    public void stopService(){
        packetHandler.setStop();
    }

    public int getServiceState(){
        return currentState;
    }

    public void addFilterIntoInterface(String interfaceName){
        packetHandler.addPacketFilter(interfaceName);
    }

    public void addProcessor(PacketProcessor processor){
        packetHandler.addProcessor(processor);
    }
}
