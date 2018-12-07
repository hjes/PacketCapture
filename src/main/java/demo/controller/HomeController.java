package demo.controller;

import com.jfoenix.controls.JFXListView;
import demo.model.ListViewModel;
import io.datafx.controller.ViewController;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import javax.annotation.PostConstruct;

/**
 * Created by Snart Lu on 2018/2/5.
 */
@ViewController(value = "/views/home.fxml")
public class HomeController{

    @FXML private BorderPane root;
    @FXML private Button home_btn_start_capturing;
    @FXML private JFXListView<String> home_list_interface;
    @FXML private JFXListView<String> home_list_packet;

    @PostConstruct
    private void init(){
//        ObservableList<String> nameList = FXCollections.observableList(PacketCaptureServiceProxy.getAllInterfacesName());
//        home_list_interface.setItems(nameList);
        ListViewModel<String> packetListViewModel = new ListViewModel<>(1000);
        ObservableList<String> packetList = FXCollections.observableList(packetListViewModel.getList());
        home_list_packet.setItems(packetList);
        //old_val表示上一次选中的对象，new_val表示新选中的，ov表示本次选中的
        home_list_packet.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val,
                 String new_val) -> {
                   System.out.println(ov.getValue() + "--" + old_val + "--" + new_val);
                });
//        home_list_packet.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//
//            }
//        });
//        nameList.add("1");
//        nameList.add("2");
//        nameList.add("3");
        packetList.add("1");
        packetList.add("2");
        packetList.add("3");
        packetList.add("4");
        packetList.add("5");
        packetList.add("6");
    }

}
