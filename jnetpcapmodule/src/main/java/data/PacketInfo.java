package data;

import org.jnetpcap.packet.JPacket;


//TODO
public class PacketInfo {

    private JPacket jPacket;
    private String topProtocol;
    private String detailInfo;
    private String timeStamp;

    public PacketInfo(JPacket jPacket){
        this.jPacket = jPacket;
    }

    public JPacket getjPacket() {
        return jPacket;
    }

    public String getTopProtocol() {
        return topProtocol;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
