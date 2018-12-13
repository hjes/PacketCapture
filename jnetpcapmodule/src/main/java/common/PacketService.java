package common;


import data.Data;
import packet.processor.PackageSender;

import java.util.List;

public interface PacketService {
    List<String> getInterfaceName();
    PackageSender getPacketSender(String interfaceName);
    boolean sendSomeData(String interfaceName, Data data);
}
