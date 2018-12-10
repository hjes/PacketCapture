package packet;

import data.PacketWrapper;
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
    public void process(PacketWrapper packetWrapper) {
//        if (bytes==null||byteLength<packet.getCaptureHeader().caplen()){
//            if(bytes!=null)
//                byteLength = packet.getCaptureHeader().caplen();
//            bytes = new byte[byteLength];
//            System.out.println("init byte 1024");
//        }
        //Transfer data to destination
        JBuffer jBuffer = new JBuffer(packetWrapper.getPcapPacket().getTotalSize());
        packetWrapper.getPcapPacket().transferTo(jBuffer);
//        packet.transferStateAndDataTo(bytes);
        System.out.println("send to " + deviceName + " " + packetWrapper.getPcapPacket().getCaptureHeader().caplen() + "bytes -- len : " + packetWrapper.getPcapPacket().getPacketWirelen());
        if (pcap.sendPacket(jBuffer) != Pcap.OK) {
            //send error
            System.err.println(pcap.getErr());
        }
    }
}
