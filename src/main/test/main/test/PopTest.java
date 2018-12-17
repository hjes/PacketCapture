package main.test;

import data.PacketWrapper;
import org.apache.http.protocol.HTTP;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.JMemoryPacket;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.JProtocol;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;
import packet.PacketCapture;
import packet.handler.JPacketHandler;
import packet.handler.PacketHandler;
import packet.model.SysInfoBean;
import javafx.application.Application;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Test;
import packet.PacketCaptureServiceProxy;
import packet.processor.LinuxSender;
import packet.processor.PacketProcessor;
import packet.processor.WinSender;
import utils.PacketUtils;

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
//        PacketCaptureServiceProxy.startCapturingPacket(nameList.get(1));
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

    @Test
    public void sendTest(){
        List<String> nameList = PacketCaptureServiceProxy.getAllInterfacesName();
        List<String> detailList = PacketCaptureServiceProxy.getAllInterfaceDetails();
        int i = 0;
        for (String s:detailList) {
            System.out.println(s);
            System.out.println("mac address : " + PacketUtils.getMacAddress(nameList.get(i)));
            i++;
        }
        String choosenName = nameList.get(0);
        String receiverName = nameList.get(nameList.size()-2);
        JPacketHandler<String> packetHandler = new JPacketHandler<>();
        JPacketHandler<String> packetHandler2 = new JPacketHandler<>();
        packetHandler.addProcessor(new PacketProcessor() {
            @Override
            public void process(PacketWrapper packetWrapper) {
                System.out.println(packetWrapper.getPcapPacket().toString());
                PacketCaptureServiceProxy.getPacketSender(receiverName).sendDatas(packetWrapper.getPcapPacket());
            }
        });
        packetHandler2.addProcessor(new PacketProcessor() {
            @Override
            public void process(PacketWrapper packetWrapper) {
                System.out.println("receive");
                PcapPacket jPacket = (PcapPacket) packetWrapper.getPcapPacket();
                System.out.println(jPacket.toString());
            }
        });
        PacketCapture.mainJCapture(choosenName,packetHandler,1);
        PacketCapture.mainJCapture(receiverName,packetHandler2,1);
    }


    @Test
    public void packetInfoTest(){
        List<String> nameList = PacketCaptureServiceProxy.getAllInterfacesName();
        for (String s:nameList)
            System.out.println(s);
        String choosenName = nameList.get(nameList.size()-1);
        JPacketHandler<String> packetHandler = new JPacketHandler<>();
        packetHandler.addProcessor(new PacketProcessor() {
            @Override
            public void process(PacketWrapper packetWrapper) {
                System.out.println("time1 : " + System.nanoTime());
//                System.out.println(packetWrapper.getPcapPacket().toString());
//                PacketCaptureServiceProxy.getPacketSender(receiverName).sendDatas(packetWrapper.getPcapPacket());
                System.out.println("send");
                PcapPacket jPacket = (PcapPacket) packetWrapper.getPcapPacket();
                System.out.println(jPacket.toString());
                System.out.println("////////////////////////");
//                System.out.println("wire length" + jPacket.getPacketWirelen());
//                System.out.println("total size  " + jPacket.getTotalSize());
//                System.out.println("has en" + jPacket.hasHeader(Ethernet.ID));
//                System.out.println("has ip" + jPacket.hasHeader(Ip4.ID));
//                System.out.println("has tcp" + jPacket.hasHeader(Tcp.ID));
//                System.out.println("has udp" + jPacket.hasHeader(Udp.ID));
//                System.out.println("has http" + jPacket.hasHeader(new Http()));
//                PcapPacket tempPacket = new PcapPacket(jPacket);
//                JPacket.State state = jPacket.getState();
//                System.out.println(state.size() + "--" + state.getWirelen());
//                System.out.println(state.getHeaderIdByIndex(0));
//                System.out.println(state.getHeaderIdByIndex(1));
//                System.out.println(state.getHeaderIdByIndex(2));
//                System.out.println(state.getHeaderIdByIndex(3));


//                System.out.println("----------------------------");
//                System.out.println("headerIndex " + state.findHeaderIndex(Ethernet.ID));
//                System.out.println("64 bit header map " + state.get64BitHeaderMap(0));
//                System.out.println("header length " + state.getHeaderLengthByIndex(2));
//                System.out.println("header offset //0 " + state.getHeaderOffsetByIndex(0));
//                System.out.println("frame number " + state.getFrameNumber());
//                System.out.println("flags : // 0 " + state.getFlags());
//                System.out.println("struct name : //packet_struct_t " + state.getStructName());
//                System.out.println("header count " + state.getHeaderCount());
//                System.out.println("TOTALSIZE before SETUP(0): " + jPacket.getTotalSize());
//                state.setSize(0);
//                System.out.println("TOTALSIZE after SETIP(0) " + jPacket.getTotalSize());
//                System.out.println(PacketUtils.getTopProtocol(jPacket));
                JPacket.State state = jPacket.getState();
                System.out.println(state.toDebugString());
                state.setSize(0);
                JBuffer jBuffer = new JBuffer(jPacket.getTotalSize());
                jPacket.transferTo(jBuffer);
                int i = 0;
                for (byte b:jBuffer.getByteArray(0,jBuffer.size())){
                    if (i%10==0)
                        System.out.print("\n");
                    System.out.print(+ b + " ");
                    i++;
                }
                System.out.println(i);
            }
        });
        PacketCaptureServiceProxy.getPcapByInterfaceName(choosenName).dispatch(1, new org.jnetpcap.packet.JPacketHandler<Object>() {
            @Override
            public void nextPacket(JPacket jPacket, Object o) {
                System.out.println("time2 : " + System.nanoTime());
                System.out.println(jPacket.hashCode());
            }
        },null);
        PacketCapture.mainJCapture(choosenName,packetHandler,1);

    }

    @Test
    public void packetTest(){
        byte[] bytes = "hello".getBytes();
        JPacket jPacket = new JMemoryPacket(bytes);
//        Ip4 ip = jPacket.getHeader(new Ip4());
//        System.out.println(ip.getDescription());
        System.out.println(jPacket.toString());

        //
        JPacket packet = new JMemoryPacket(JProtocol.ETHERNET_ID,
                " 001801bf 6adc0025 4bb7afec 08004500 " +
                        " 0041a983 40004006 d69ac0a8 00342f8c " +
                        " ca30c3ef 008f2e80 11f52ea8 4b578018 " +
                        " ffffa6ea 00000101 080a152e ef03002a " +
                        " 2c943538 322e3430 204e4f4f 500d0a");
        Ip4 ip = packet.getHeader(new Ip4());
//        Tcp tcp = packet.getHeader(new Tcp());
//        tcp.destination(80);
        ip.checksum(ip.calculateChecksum());
//        tcp.checksum(tcp.calculateChecksum());
        packet.scan(Ethernet.ID);
        System.out.println(packet);

    }

    @Test
    public void macTest(){
        for (String string:PacketCaptureServiceProxy.getAllInterfacesName())
            System.out.println(string);
        System.out.println(PacketUtils.getMacAddress("enp0s5"));
    }
}
