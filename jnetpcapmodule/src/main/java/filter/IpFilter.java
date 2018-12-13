package filter;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.network.Ip4;

public class IpFilter implements PacketFilter {
    private Ip4 ip4 = new Ip4();
    @Override
    public boolean packetFilter(PcapPacket packet) {
        return packet.hasHeader(ip4);
    }

    @Override
    public String getFilterName() {
        return "IP";
    }
}
