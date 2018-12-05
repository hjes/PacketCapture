package data;

import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;

public interface Data {
    PcapPacket getPacket();
    JBuffer getJBuffer();
    byte[] getBytes();
}
