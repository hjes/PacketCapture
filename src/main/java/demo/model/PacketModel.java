package demo.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PacketModel extends RecursiveTreeObject<PacketModel> {
    final StringProperty id;
    final StringProperty packetLength;
    final StringProperty packetProtocol;
    final StringProperty packetTime;
    final StringProperty packetInfo;

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
}
