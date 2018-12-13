package demo.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jnetpcap.packet.PcapPacket;

public class PacketModel extends RecursiveTreeObject<PacketModel> {
    final StringProperty id;
    final StringProperty packetLength;
    final StringProperty packetProtocol;
    final StringProperty packetTime;
    final StringProperty packetInfo;
    private PcapPacket pcapPacket;

    public PacketModel(String id,String packetTime,PcapPacket pcapPacket,String info){
        this(id,packetTime,String.valueOf(pcapPacket.getTotalSize()),pcapPacket.getCaptureHeader().getStructName(),null);
        this.pcapPacket = pcapPacket;
    }

    public PacketModel(String id,String packetTime,String packetLength,String packetProtocol,String packetInfo){
        this.id = new SimpleStringProperty(id);
        this.packetLength = new SimpleStringProperty(packetLength);
        this.packetProtocol = new SimpleStringProperty(packetProtocol);
        this.packetTime = new SimpleStringProperty(packetTime);
        this.packetInfo = new SimpleStringProperty(packetInfo);
    }
    public StringProperty idProperty() {
        return id;
    }

    public StringProperty packetLengthProperty() {
        return packetLength;
    }

    public StringProperty packetProtocolProperty(){
        return packetProtocol;
    }

    public StringProperty packetTimeProperty(){return packetTime;}

    public StringProperty packetInfoProperty(){return packetInfo;}

    public PcapPacket getPcapPacket() {
        return pcapPacket;
    }
}
