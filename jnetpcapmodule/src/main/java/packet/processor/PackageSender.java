package packet.processor;

import common.Common;
import common.Observer;
import common.ObserverCenter;
import data.PacketWrapper;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.PcapPacket;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;


 public class PackageSender extends Thread implements PacketProcessor {

    private LinkedBlockingQueue<JPacket> packetQueue;
    private List<Observer<String>> observerList;
    private String deviceName;
    private PacketProcessor packetProcessor;
    private boolean hasStarted = false;

    private PackageSender(){
        packetQueue = new LinkedBlockingQueue<>(1000);
        observerList = new LinkedList<>();
    }

    public PackageSender(String deviceName){
        this();
        this.deviceName = deviceName;
        packetProcessor = new WinSender(deviceName);
    }

    /**
     * 往队列中添加数据包，如果阻塞的话返回false
     * @param packet 接收到的数据包
     * @return
     */
    private boolean addPacket(JPacket packet){
        return packetQueue.offer(packet);
    }

    @Override
    public void run() {
        //不断从ArrayQueue中取出数据包处理
        for(;;){
            try {
                JPacket packet = packetQueue.take();
                packetProcessor.process(new PacketWrapper(packet));
            } catch (InterruptedException e) {
//                e.printStackTrace();
                for (Observer<String> observer:observerList){
                    observer.update(e.toString());
                    ObserverCenter.notifyLogging("发送数据异常：" + e.toString());
                }
            }
        }
    }

    private void startService(){
        Common.executor.execute(this);
    }

    @Override
    public void process(PacketWrapper packetWrapper) {
        if (!hasStarted) {
            ObserverCenter.notifyLogging(deviceName + "服务未开始，正在开始服务...");
            startService();
            ObserverCenter.notifyLogging(deviceName + "服务开启成功");
            hasStarted = true;
        }
        addPacket(packetWrapper.getPcapPacket());
    }

    public PackageSender sendDatas(JPacket pcapPacket){
        process(new PacketWrapper(pcapPacket));
        return this;
    }
}
