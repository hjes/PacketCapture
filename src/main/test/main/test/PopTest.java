package main.test;

import data.PacketWrapper;
import demo.model.SysInfoBean;
import javafx.application.Application;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.junit.Test;
import packet.PacketCaptureServiceProxy;
import packet.processor.PacketProcessor;

import java.util.List;


public class PopTest extends Application {
    ObservableList<SysInfoBean> sysInfoBeans;
    ObservableObjectValue<SysInfoBean> sysInfoBeanObservableObjectValue;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JFX Popup Demo");
        primaryStage.setResizable(false);
        StackPane stackPane = new StackPane();
        Button button = new Button("click");
        stackPane.getChildren().add(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
        Scene scene = new Scene(stackPane,500,500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Test
    public void dialogTest(){
    }


    @Test
    public void diaptcherTest() {
        List<String> nameList = PacketCaptureServiceProxy.getAllInterfacesName();
        PacketCaptureServiceProxy.startCapturingPacket(nameList.get(1));
//        PacketCaptureServiceProxy.startCapturingPacket(nameList.get(1));
//        PacketCaptureServiceProxy.startCapturingPacket(nameList.get(2));
        PacketCaptureServiceProxy.addProcessor(nameList.get(1), new PacketProcessor() {
            @Override
            public void process(PacketWrapper packetWrapper) {
//                PacketCaptureServiceProxy.getPacketSender(nameList.get(1)).sendDatas(packetWrapper.getPcapPacket());
//                PacketCaptureServiceProxy.getPacketSender(nameList.get(2)).sendDatas(packetWrapper.getPcapPacket());
            }
        });
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
