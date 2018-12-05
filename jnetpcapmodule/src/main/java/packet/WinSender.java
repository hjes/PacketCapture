package packet;

import org.jnetpcap.Pcap;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.winpcap.WinPcap;

public class WinSender implements PacketProcessor{
    private int snaplen; // Capture all packets, no trucation
    private int flags; // capture all packets
    private int timeout; // 10 seconds in millis
    private StringBuilder errbuf; // For any error msgs
    private String deviceName;
    private Pcap pcap;

    /**
     * init
     * @param deviceName 转发的设备名称
     */
    public WinSender(String deviceName){
        this.deviceName = deviceName;
        snaplen = 64 * 1024;
        flags = Pcap.MODE_PROMISCUOUS;
        timeout = 10 * 1000;
        errbuf = new StringBuilder();
        pcap = WinPcap.openLive(deviceName, snaplen, flags, timeout, errbuf);
    }

    @Override
    public void process(PcapPacket packet) {
//        if (bytes==null||byteLength<packet.getCaptureHeader().caplen()){
//            if(bytes!=null)
//                byteLength = packet.getCaptureHeader().caplen();
//            bytes = new byte[byteLength];
//            System.out.println("init byte 1024");
//        }
        //Transfer data to destination
        JBuffer jBuffer = new JBuffer(packet.getTotalSize());
        packet.transferTo(jBuffer);
//        packet.transferStateAndDataTo(bytes);
        System.out.println("send to " + deviceName + " " + packet.getCaptureHeader().caplen() + "bytes -- len : " + packet.getPacketWirelen());
        if (pcap.sendPacket(jBuffer) != Pcap.OK) {
            //send error
            System.err.println(pcap.getErr());
        }
    }
}
