package packet.interfaces;
import data.PacketWrapper;
import org.jnetpcap.packet.PcapPacket;

public interface PacketProcessor{
    void process(PacketWrapper packetWrapper);
}
