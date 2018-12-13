package filter;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.tcpip.Http;

public class HttpFilter implements PacketFilter {
    private Http http = new Http();
    @Override
    public boolean packetFilter(PcapPacket packet) {
        return packet.hasHeader(http);
    }

    @Override
    public String getFilterName() {
        return "TCP";
    }
}
