package demo.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PacketModel extends RecursiveTreeObject<PacketModel> {
    final StringProperty id;
    final StringProperty packetLength;
    final StringProperty packetProtocol;
    public PacketModel(String id,String packetLength,String packetProtocol){
        this.id = new SimpleStringProperty(id);
        this.packetLength = new SimpleStringProperty(packetLength);
        this.packetProtocol = new SimpleStringProperty(packetProtocol);
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
}
