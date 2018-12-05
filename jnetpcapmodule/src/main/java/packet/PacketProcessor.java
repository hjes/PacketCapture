package packet;
import org.jnetpcap.packet.PcapPacket;

public interface PacketProcessor {
    void process(PcapPacket packet);
}
