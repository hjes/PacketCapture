package filter;

import org.jnetpcap.packet.PcapPacket;

/**
 * 包过滤
 */
public interface PacketFilter {
    boolean packetFilter(PcapPacket packet);
    String getFilterName();
}
