package packet;

import common.ThreadObserver;
import data.PacketWrapper;
import filter.MultiPacketFilter;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;

public class PacketHandler<T> implements PcapPacketHandler<T> {

    private ProcessorThread processorAndObserveThread = new ProcessorThread();
    private MultiPacketFilter multiPacketFilter;//包过滤器
    private ThreadObserver threadObserver = new ThreadObserver();
    private boolean needWait = false;

    public void start(){
        processorAndObserveThread.start();
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
        if (needWait)
            threadObserver.waitNow();

        if (multiPacketFilter!=null)//如果满足过滤要求则对该包进行处理
        {
            if (multiPacketFilter.packetFilter(packet))
                processorAndObserveThread.process(new PacketWrapper(packet));
        }
        else{//不设置过滤器
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
