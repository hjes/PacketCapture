package packet.handler;

import data.PacketWrapper;
import org.jnetpcap.packet.JPacket;
import packet.processor.PacketProcessor;

public class JPacketHandler<T> implements org.jnetpcap.packet.JPacketHandler<T> {

    private PacketProcessor packetProcessor;

    public void addProcessor(PacketProcessor packetProcessor){
        this.packetProcessor = packetProcessor;
    }
    @Override
    public void nextPacket(JPacket jPacket, T t) {
        packetProcessor.process(new PacketWrapper(jPacket));
    }
}
