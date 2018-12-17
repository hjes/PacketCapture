package packet.processor;

import data.PacketWrapper;
import org.jnetpcap.Pcap;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.winpcap.WinPcap;
import packet.PacketCaptureServiceProxy;
import packet.processor.PacketProcessor;

public class WinSender implements PacketProcessor {
    private String deviceName;
    private Pcap pcap;

    /**
     * init
     * @param deviceName 转发的设备名称
     */
    public WinSender(String deviceName){
        this.deviceName = deviceName;
        pcap = PacketCaptureServiceProxy.getPcapByInterfaceName(deviceName);
    }

    @Override
    public void process(PacketWrapper packetWrapper) {
        //TODO gai hiu lai
//        JBuffer jBuffer = new JBuffer(packetWrapper.getPcapPacket().getTotalSize());
//        packetWrapper.getPcapPacket().transferTo(jBuffer);
//        if (pcap.sendPacket(jBuffer) != Pcap.OK) {
//            //send error
//            System.err.println(pcap.getErr());
//        }
        //test
        if (pcap.sendPacket(packetWrapper.getPcapPacket()) != Pcap.OK) {
            //send error
            System.err.println(pcap.getErr());
        }
    }
}
