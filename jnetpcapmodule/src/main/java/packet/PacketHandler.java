package packet;

import common.Observer;
import common.ObserverCenter;
import common.ThreadObserver;
import data.PacketWrapper;
import filter.MultiPacketFilter;
import org.jnetpcap.Pcap;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import packet.processor.PacketProcessor;

public class PacketHandler<T> implements PcapPacketHandler<T> {

    private ProcessorThread processorAndObserveThread = new ProcessorThread();
    private MultiPacketFilter multiPacketFilter;//包过滤器
    private ThreadObserver threadObserver = new ThreadObserver();
    private boolean needWait = false;
    //private boolean stopService = false;
    private String interfaceName;
    private Pcap pcap;

    public void start(){
        processorAndObserveThread.start();
    }

    public PacketHandler(String interfaceName){
        this.interfaceName = interfaceName;
    }

    /**
     * 设置包过滤器
     * @param multiPacketFilter 如果之前已经往里面添加过过滤器，那就在此基础上添加
     */
    public void setPacketFilter(MultiPacketFilter multiPacketFilter){
        if (this.multiPacketFilter!=null)//如果已有的不为空
            multiPacketFilter.addPacketFilters(multiPacketFilter.getAllFilterName());
         else   //为空
            this.multiPacketFilter = multiPacketFilter;
    }

    /**
     * 添加过滤器
     * @param name 过期的名字
     * @return 链式，继续添加
     */
    public PacketHandler addPacketFilter(String name){
        if (multiPacketFilter==null)
            multiPacketFilter = new MultiPacketFilter();
        multiPacketFilter.addPacketFilter(name);
        return this;
    }

    public void setPause(){
        needWait = true;
    }

    public void setConsume(){
        needWait = false;
        threadObserver.notifyNow();
    }
    public void setStop(){
        processorAndObserveThread.stopService();
        if (needWait){
            threadObserver.notifyNow();
            needWait = false;
        }
        pcap.breakloop();
        processorAndObserveThread.interrupt();
        ObserverCenter.notifyLogging("break capture loop success");
        if (!processorAndObserveThread.isAlive()){
            ObserverCenter.notifyLogging("close process thread success");
        }else{
            ObserverCenter.notifyLogging("close process thread fail");
            if (!processorAndObserveThread.getIsStopService()){
                //由于队列阻塞无法停止，手动加入一个让其最后运行一次停止即可
                processorAndObserveThread.process(new PacketWrapper(new PcapPacket(new byte[]{
                        1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16//invalid [16,0)range
                })));
            }
        }

    }

    public void addProcessor(PacketProcessor packetProcessor){
        processorAndObserveThread.addProcessor(packetProcessor);
    }

    public void setPcap(Pcap pcap){
        this.pcap = pcap;
    }

    @Override
    public void nextPacket(PcapPacket packet, T user) {
        /*Http http = new Http();
            Tcp tcp = new Tcp();
            if (!packet.hasHeader(tcp)) {
                return;
            }
         System.out.printf("Received packet at %s caplen=%-4d len=%-4d %s\n",
         new Date(packet.getCaptureHeader().timestampInMillis()), packet
         .getCaptureHeader().caplen(), // Length
         // actually
         // captured
         packet.getCaptureHeader().wirelen(), // Original
         // length
         user // User supplied object
         );
         */
        //找到合适的packet就添加到队列中发送出去
        // packageSender.process(packet);
//            if (stopService) {
//                pcap.breakloop();
//                ObserverCenter.notifyLogging("break the loop");
//            }
            if (needWait)//TODO 这里的暂停只是把包阻塞在那里，consume之后之前的包还是会全部重新塞进去 加一个跳包的功能，即不往队列里面加就好了
                threadObserver.waitNow();

            if (multiPacketFilter != null)//如果满足过滤要求则对该包进行处理
            {
                if (multiPacketFilter.packetFilter(packet))
                    processorAndObserveThread.process(new PacketWrapper(packet));
            } else {//不设置过滤器
                processorAndObserveThread.process(new PacketWrapper(packet));
            }

        /*
        String contend = packet.toString();
        if (contend.contains("DDDDD")&&contend.contains("upass")) {
            System.out.println(contend);
        }
         }
         System.out.println( http.getPacket().toString());
         System.out.println(contend);
         String hexdump=packet.toHexdump(packet.size(), false, true,
         false);
         byte[] data = FormatUtils.toByteArray(hexdump);
        Ethernet eth = new Ethernet(); // Preallocate our ethernet
        // header
        Ip4 ip = new Ip4(); // Preallocat IP version 4 header

        Tcp tcp = new Tcp();

        Udp udp = new Udp();

         Http http=new Http();
         if (packet.hasHeader(eth)) {
         System.out.printf("ethernet.type=%X\n", eth.type());
         }

         if (packet.hasHeader(ip)) {
         System.out.printf("ip.version=%d\n", ip.version());
         }*/
    }
}
