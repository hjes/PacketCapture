package packet;

import common.ObserverCenter;
import org.jnetpcap.packet.PcapPacket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class ProcessorThread extends Thread implements PacketProcessor {
    private List<PacketProcessor> packetProcessorList = new LinkedList<>();
    private int currentSize;
    private int totalSize = 1500;
    private LinkedBlockingQueue<PcapPacket> pcapPacketConcurrentLinkedQueue = new LinkedBlockingQueue<>(totalSize);

    /**
     * 初始化时添加
     * @param packetProcessors
     */
    public ProcessorThread(List<PacketProcessor> packetProcessors){
        packetProcessorList.addAll(packetProcessors);
    }

    /**
     * 添加packet到队列
     * @param packet 需要处理的包
     */
    @Override
    public void process(PcapPacket packet) {
        if(pcapPacketConcurrentLinkedQueue.add(packet))
            currentSize++;
    }

    /**
     * 从队列中不断抓取包迭代调用process处理packet
     */
    @Override
    public void run() {
        for (;;){
            for (PacketProcessor packetProcessor:packetProcessorList){
                try {
                    packetProcessor.process(pcapPacketConcurrentLinkedQueue.take());
                } catch (InterruptedException e) {
                    ObserverCenter.notifyObservers(this.getClass().getName(),e.toString() + "\n" + " in ProcessorThread");
                }
            }
        }
    }

    /**
     * @param packetProcessor 添加处理器
     */
    public void addProcessor(PacketProcessor packetProcessor){
        packetProcessorList.add(packetProcessor);
    }

}
