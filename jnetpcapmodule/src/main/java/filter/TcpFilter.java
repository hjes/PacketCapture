package filter;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.tcpip.Tcp;

public class TcpFilter implements PacketFilter {

    private Tcp tcp = new Tcp();

    @Override
    public boolean packetFilter(PcapPacket packet) {
        return packet.hasHeader(tcp);
    }

    @Override
    public String getFilterName() {
        return "TCP";
    }
}
