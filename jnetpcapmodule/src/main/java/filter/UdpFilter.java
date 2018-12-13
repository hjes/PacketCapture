package filter;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.tcpip.Udp;

public class UdpFilter implements PacketFilter {
    //TODO 这样写是否可以
    private Udp udp = new Udp();

    @Override
    public boolean packetFilter(PcapPacket packet) {
        return packet.hasHeader(udp);
    }

    @Override
    public String getFilterName() {
        return "UDP";
    }
}
