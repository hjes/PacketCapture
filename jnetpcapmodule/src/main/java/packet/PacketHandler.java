package packet;

import org.jnetpcap.PcapDumper;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.tcpip.Tcp;

import java.util.Date;

public class PacketHandler<T> implements PcapPacketHandler<T> {

    private PcapDumper pcapDumper;
    private String deviceName;
    private PackageSender packageSender;

    public void setPcapDumper(PcapDumper pcapDumper){
        this.pcapDumper = pcapDumper;
    }

    public void setSenderDeviceName(String deviceName){
        this.deviceName = deviceName;
        //sender init
        packageSender = new PackageSender(deviceName);
    }

    @Override
    public void nextPacket(PcapPacket packet, T user) {
        //Http http = new Http();
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

        //找到合适的packet就添加到队列中发送出去
        packageSender.process(packet);

        String contend = packet.toString();
        if (contend.contains("DDDDD")&&contend.contains("upass")) {
            System.out.println(contend);
        }
        // }
        // System.out.println( http.getPacket().toString());
        // System.out.println(contend);
        // String hexdump=packet.toHexdump(packet.size(), false, true,
        // false);
        // byte[] data = FormatUtils.toByteArray(hexdump);
//        Ethernet eth = new Ethernet(); // Preallocate our ethernet
//        // header
//        Ip4 ip = new Ip4(); // Preallocat IP version 4 header
//
//        Tcp tcp = new Tcp();
//
//        Udp udp = new Udp();

        // Http http=new Http();
        // if (packet.hasHeader(eth)) {
        // System.out.printf("ethernet.type=%X\n", eth.type());
        // }
        //
        // if (packet.hasHeader(ip)) {
        // System.out.printf("ip.version=%d\n", ip.version());
        // }

    }
}
