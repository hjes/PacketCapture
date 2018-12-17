package packet.model;

import packet.model.ListViewModel;
import packet.model.PacketModel;
import packet.processor.PacketProcessor;

public abstract class PacketTableProcessModel  implements PacketProcessor {
    public ListViewModel<PacketModel> threadOwnerModel;
    public int packetID = 0;
    public String interfaceName;
    public PacketTableProcessModel(ListViewModel<PacketModel> threadOwnerModel,String interfaceName){
        this.threadOwnerModel = threadOwnerModel;
        this.interfaceName = interfaceName;
    }
}
