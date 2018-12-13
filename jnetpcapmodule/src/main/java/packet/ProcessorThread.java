package packet;

import common.ObserverCenter;
import data.PacketWrapper;
import org.jnetpcap.packet.PcapPacket;
import packet.processor.PacketProcessor;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static common.Common.PACKET_LOSE_EVENT;

/**
 * 处理包的线程
 */
public class ProcessorThread extends Thread implements PacketProcessor {
    private List<PacketProcessor> packetProcessorList = new LinkedList<>();
    private double currentSize;
    private double lossSize;
    private int totalSize = 1500;
    private LinkedBlockingQueue<PcapPacket> pcapPacketConcurrentLinkedQueue = new LinkedBlockingQueue<>(totalSize);
    private boolean stopService = false;
    private boolean isStopService = false;

    public void pauseTheThread(){

    }
    /**
     * 初始化时添加
     * @param packetProcessors
     */
    public ProcessorThread(List<PacketProcessor> packetProcessors){
        packetProcessorList.addAll(packetProcessors);
    }

    public ProcessorThread(){
    }

    /**
     * 添加packet到队列
     * @param packetWrapper 需要处理的包
     * add，当queue满时抛出异常，offer返回false
     */
    @Override
    public void process(PacketWrapper packetWrapper) {
        if(pcapPacketConcurrentLinkedQueue.offer(packetWrapper.getPcapPacket()))
            currentSize++;
        else{
            lossSize++;
            currentSize++;
            ObserverCenter.notifyObservers(PACKET_LOSE_EVENT,String.valueOf(lossSize/currentSize));
        }
    }

    public boolean getIsStopService(){
        return isStopService;
    }

    /**
     * 从队列中不断抓取包迭代调用process处理packet
     */
    @Override
    public void run() {
            for (; ; ) {
                if (stopService){
                    isStopService = true;
                    ObserverCenter.notifyLogging("break in process thread");
                    break;
                }
                PcapPacket tempPcapPacket = null;
                try {
                    tempPcapPacket = pcapPacketConcurrentLinkedQueue.take();
                } catch (InterruptedException e) {
                    ObserverCenter.notifyObservers(this.getClass().getName(), e.toString() + "\n" + " in ProcessorThread");
                }
                //将抓取到的包传递给process进行处理
                if (tempPcapPacket != null)
                    for (PacketProcessor packetProcessor : packetProcessorList) {
                        packetProcessor.process(new PacketWrapper(tempPcapPacket).addObject(PacketWrapper.TIME, new Date()));
                    }
            }
    }

    /**
     * @param packetProcessor 添加处理器
     */
    public void addProcessor(PacketProcessor packetProcessor){
        packetProcessorList.add(packetProcessor);
    }

    public void stopService(){
        stopService = true;
    }

}
