package packet;

import common.ObserverCenter;
import org.jnetpcap.packet.PcapPacket;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static common.Common.PACKET_LOSE_EVENT;

public class ProcessorAndObserveThread extends Thread implements PacketProcessor {
    private static List<PacketProcessor> packetProcessorList = new LinkedList<>();
    private double currentSize;
    private double lossSize;
    private int totalSize = 1500;
    private LinkedBlockingQueue<PcapPacket> pcapPacketConcurrentLinkedQueue = new LinkedBlockingQueue<>(totalSize);

    /**
     * 初始化时添加
     * @param packetProcessors
     */
    public ProcessorAndObserveThread(List<PacketProcessor> packetProcessors){
        packetProcessorList.addAll(packetProcessors);
    }

    public ProcessorAndObserveThread(){
    }

    /**
     * 添加packet到队列
     * @param packet 需要处理的包
     * add，当queue满时抛出异常，offer返回false
     */
    @Override
    public void process(PcapPacket packet) {
        if(pcapPacketConcurrentLinkedQueue.offer(packet))
            currentSize++;
        else{
            lossSize++;
            currentSize++;
            ObserverCenter.notifyObservers(PACKET_LOSE_EVENT,String.valueOf(lossSize/currentSize));
        }
    }

    /**
     * 从队列中不断抓取包迭代调用process处理packet
     */
    @Override
    public void run() {
        for (;;){
            PcapPacket tempPcapPacket = null;
            try {
                tempPcapPacket = pcapPacketConcurrentLinkedQueue.take();
            } catch (InterruptedException e) {
                ObserverCenter.notifyObservers(this.getClass().getName(),e.toString() + "\n" + " in ProcessorThread");
            }
            //将抓取到的包传递给process进行处理
            if (tempPcapPacket!=null)
                for (PacketProcessor packetProcessor:packetProcessorList){
                    packetProcessor.process(tempPcapPacket);
                }
        }
    }

    /**
     * @param packetProcessor 添加处理器
     */
    public static void addProcessor(PacketProcessor packetProcessor){
        packetProcessorList.add(packetProcessor);
    }

}
