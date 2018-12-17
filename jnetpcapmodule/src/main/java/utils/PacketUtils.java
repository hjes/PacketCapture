package utils;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.JProtocol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketUtils {
    public static String getTopProtocol(JPacket packet){
        JPacket.State state = packet.getState();
        int i = state.getHeaderCount()-1;
        if (state.getHeaderIdByIndex(i)==0){
            return JProtocol.valueOf(state.getHeaderIdByIndex(i-1)).toString();
        }else
            return JProtocol.valueOf(state.getHeaderIdByIndex(i)).toString();
    }


    public static String getMacAddress(String interfaceName) {
        byte[] mac = null;
        List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs
        StringBuilder errbuf = new StringBuilder(); // For any error msgs

        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices, error is %s", errbuf.toString());
            return null;
        } /***************************************************************************
         * 获取硬件地址
         **************************************************************************/
        for (final PcapIf i : alldevs) {
            try {
                if (i.getName().equals(interfaceName))
                    mac = i.getHardwareAddress();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mac == null) {
                continue; // 当接口不含有硬件地址的时候 } System.out.printf("%s=%s\n", i.getName(), asString(mac)); } }
            }
        }
        final StringBuilder buf = new StringBuilder();
        if (mac!=null) {
            for (byte b : mac) {
                if (buf.length() != 0) {
                    buf.append(':');
                }
                if (b >= 0 && b < 16) {
                    buf.append('0');
                }
                buf.append(Integer.toHexString((b < 0) ? b + 256 : b).toUpperCase());
            }
        }
        return buf.toString();
    }

    public static String getPacketDebugInfo(JPacket packet){
        return packet.getState().toDebugString();
    }
    public static byte[] getPacketData(JPacket packet){
        return null;
    }


}
