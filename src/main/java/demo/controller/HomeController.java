package demo.controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import common.Observer;
import common.ObserverCenter;
import demo.model.ListViewModel;
import demo.model.PacketModel;
import io.datafx.controller.ViewController;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import org.jnetpcap.packet.PcapPacket;
import packet.PackageCapture;
import packet.PacketCaptureServiceProxy;
import packet.PacketProcessor;
import packet.ProcessorAndObserveThread;


import javax.annotation.PostConstruct;
import java.util.function.Function;

import static common.Common.PACKET_LOSE_EVENT;

/**
 * Created by Snart Lu on 2018/2/5.
 */
@ViewController(value = "/views/home.fxml")
public class HomeController{

    @FXML private BorderPane root;
    @FXML private Button home_btn_start_capturing;
    @FXML private JFXListView<String> home_list_interface;

    /**
     * table settings
     */
    @FXML
    private JFXTreeTableView<PacketModel> home_table_packet;
    @FXML
    private JFXTreeTableColumn<PacketModel, String> packetIDColumn;
    @FXML
    private JFXTreeTableColumn<PacketModel, String> packetLengthColumn;
    @FXML
    private JFXTreeTableColumn<PacketModel, String> packetProtocolColumn;
    @FXML
    private Button home_btn_lose_rate;

    //当前已经抓到的包的数量，用于填充packetID
    private long packetNumber = 0;

    private ObservableList<PacketModel> dummyData;

    @PostConstruct
    private void init(){
        ObservableList<String> nameList = FXCollections.observableList(PacketCaptureServiceProxy.getAllInterfacesName());
        home_list_interface.setItems(nameList);
        //old_val表示上一次选中的对象，new_val表示新选中的，ov表示本次选中的
        //可以设置监听index或者content
        home_list_interface.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val,
                 String new_val) -> {
                   System.out.println(ov.getValue() + "--" + old_val + "--" + new_val);
                });

        home_btn_start_capturing.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        PackageCapture.mainCapture(new String[]{"1"});
                    }
                }).start();
                home_btn_start_capturing.setDisable(true);
            }
        });

        setupPacketTableView();

        ProcessorAndObserveThread.addProcessor(new PacketProcessor() {
            @Override
            public void process(PcapPacket packet) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        dummyData.add(new PacketModel(String.valueOf(packetNumber),
                        String.valueOf(packet.getTotalSize()), "TCP")) ;
                        packetNumber++;
                    }
                });
            }
        });

        ObserverCenter.register(PACKET_LOSE_EVENT, new Observer<String>() {
            @Override
            public void update(String s) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        home_btn_lose_rate.setText(s);
                    }
                });
            }
        });
    }

    /**
     * 用户初始化packetTable
     */
    private void setupPacketTableView() {
        setupCellValueFactory(packetIDColumn, PacketModel::idProperty);
        setupCellValueFactory(packetLengthColumn, PacketModel::packetLengthProperty);
        setupCellValueFactory(packetProtocolColumn, PacketModel::packetProtocolProperty);

        //设置tableView和数据之间的绑定
        dummyData = FXCollections.observableArrayList();
        home_table_packet.setRoot(new RecursiveTreeItem<>(dummyData, RecursiveTreeObject::getChildren));
        home_table_packet.setEditable(true);
        home_table_packet.setShowRoot(false);
    }

    /**
     * 属性绑定方法
     */
    private <T> void setupCellValueFactory(JFXTreeTableColumn<PacketModel, T> column, Function<PacketModel, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<PacketModel, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

}
