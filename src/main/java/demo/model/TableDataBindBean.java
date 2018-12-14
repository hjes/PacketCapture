package demo.model;

import javafx.collections.ObservableList;

public class TableDataBindBean {
    private ListViewModel<PacketModel> packetModelListViewModel;
    private ObservableList<PacketModel> packetModelObservableList;

    public TableDataBindBean(ListViewModel<PacketModel> packetModelListViewModel,
                             ObservableList<PacketModel> packetModelObservableList){
        this.packetModelListViewModel = packetModelListViewModel;
        this.packetModelObservableList = packetModelObservableList;
    }

    public ListViewModel<PacketModel> getPacketModelListViewModel() {
        return packetModelListViewModel;
    }

    public void setPacketModelListViewModel(ListViewModel<PacketModel> packetModelListViewModel) {
        this.packetModelListViewModel = packetModelListViewModel;
    }

    public ObservableList<PacketModel> getPacketModelObservableList() {
        return packetModelObservableList;
    }

    public void setPacketModelObservableList(ObservableList<PacketModel> packetModelObservableList) {
        this.packetModelObservableList = packetModelObservableList;
    }
}
