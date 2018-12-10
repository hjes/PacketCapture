package data;

import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;

public class PacketEntity implements Data {
    private PcapPacket pcapPacket;
    public PacketEntity(PcapPacket packet){
        this.pcapPacket = packet;
    }

    @Override
    public PcapPacket getPacket() {
        return pcapPacket;
    }

    @Override
    public JBuffer getJBuffer() {
        JBuffer jBuffer = new JBuffer(pcapPacket.getTotalSize());
        pcapPacket.transferStateAndDataTo(jBuffer);
        return jBuffer;
    }

    @Override
    public byte[] getBytes() {
        byte[] bytes = new byte[pcapPacket.getTotalSize()];
        pcapPacket.transferStateAndDataFrom(bytes);
        return bytes;
    }
}
