package filter;

import org.jnetpcap.packet.PcapPacket;

public class TcpFilter implements PacketFilter {
    @Override
    public boolean packetFilter(PcapPacket packet) {
        return false;
    }

    @Override
    public String getFilterName() {
        return "TCP";
    }
}
