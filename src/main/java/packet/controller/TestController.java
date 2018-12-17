package packet.controller;


import common.Observer;
import common.ObserverCenter;
import data.PacketWrapper;
import io.datafx.controller.ViewController;
import io.datafx.controller.context.FXMLViewContext;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import org.jnetpcap.packet.JPacket;
import packet.PacketCapture;
import packet.PacketCaptureServiceProxy;
import packet.handler.JPacketHandler;
import packet.mvc.AbstractController;
import packet.processor.LinuxSender;
import packet.processor.PacketProcessor;
import packet.repository.HomeRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@ViewController(value = "/views/test.fxml")
public class TestController extends AbstractController<HomeRepository> {
    @FXML
    private TextArea test_textarea;
    @FXML
    private Button test_send;
    @FXML
    private TextArea test_input;

    @PostConstruct
    public void init(){
        ObserverCenter.register("test_textarea", new Observer<String>() {
            @Override
            public void update(String s) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        test_textarea.appendText("\n" + s);
                    }
                });
            }
        });

        List<String> nameList = PacketCaptureServiceProxy.getAllInterfacesName();
        JPacketHandler<String> jPacketHandler = new JPacketHandler<>();
        jPacketHandler.addProcessor(new PacketProcessor() {
            @Override
            public void process(PacketWrapper packetWrapper) {
                JPacket jPacket = packetWrapper.getPcapPacket();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        test_textarea.appendText("data : " + jPacket.toString());
                    }
                });
            }
        });
        PacketCapture.mainJCapture(nameList.get(6),jPacketHandler,-1);
        LinuxSender linuxSender = new LinuxSender(nameList.get(6));
        test_send.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("test_input : " + test_input.getText());
                linuxSender.process(new PacketWrapper(null).addObject("test",test_input.getText()));
            }
        });



    }

    @Override
    public void destroy() {

    }

    @Override
    public HomeRepository initRepository() {
        return null;
    }
}
