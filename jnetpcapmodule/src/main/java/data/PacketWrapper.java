package data;

import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.PcapPacket;

import java.util.HashMap;

public class PacketWrapper {
    public static final String TIME = "packet_time";
    private JPacket pcapPacket;
    private HashMap<Object,Object> messageMap;
    public PacketWrapper(JPacket pcapPacket){
        this.pcapPacket = pcapPacket;
    }

    public PacketWrapper addObject(Object obj1,Object obj2){
        if (messageMap==null)
            messageMap = new HashMap<>(5);
        messageMap.put(obj1,obj2);
        return this;
    }

    public Object getObject(Object key){
        return messageMap.get(key);
    }

    public JPacket getPcapPacket(){
        return pcapPacket;
    }

}
