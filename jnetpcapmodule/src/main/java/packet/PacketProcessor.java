package packet;
import org.jnetpcap.packet.PcapPacket;

public interface PacketProcessor <T>{
    void process(PcapPacket packet);
}
