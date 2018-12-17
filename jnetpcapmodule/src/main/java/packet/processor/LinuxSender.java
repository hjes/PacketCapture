package packet.processor;

import data.PacketWrapper;
import org.jnetpcap.Pcap;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.winpcap.WinPcap;
import packet.PacketCaptureServiceProxy;

public class LinuxSender implements PacketProcessor{
    private String deviceName;
    private Pcap pcap;

    /**
     * init
     * @param deviceName 转发的设备名称
     */
    public LinuxSender(String deviceName){
        this.deviceName = deviceName;
        pcap = PacketCaptureServiceProxy.getPcapByInterfaceName(deviceName);
    }

    @Override
    public void process(PacketWrapper packetWrapper) {
        String data = (String) packetWrapper.getObject("test");
        if(!(pcap.sendPacket(data.getBytes())==Pcap.OK)){
            System.err.println("send fail");
        }else{
            System.out.println("send " + data + "to " + deviceName + " success");
        }
    }
}
