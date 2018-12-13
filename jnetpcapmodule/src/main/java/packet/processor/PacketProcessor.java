package packet.processor;
import data.PacketWrapper;

public interface PacketProcessor{
    void process(PacketWrapper packetWrapper);
}
