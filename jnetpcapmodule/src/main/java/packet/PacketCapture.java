package packet;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.PcapIf;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PacketCapture {
    public static final List<String> capturingInterfaces = new ArrayList<>(5);

    /**
     * 根据接口index获取设备
     */
//    public static void mainCapture(){
//        Scanner scanner =new Scanner(System.in);
//        int i = 0;
//        System.out.println("All Interface Name");
//        List<String> nameList = .getAllInterfacesName();
//        for (String interfaceName:nameList){
//            System.out.println("#" + i + " : "+ interfaceName);
//            i++;
//        }
//        System.out.println("input interface Index");
//        mainCapture(nameList.get(1));
//    }

    /**
     * 根据接口名字运行设备
     * @param interfaceName
     */
    public static void mainCapture(String interfaceName,PacketHandler<String> packetHandler) {
        //if has started capturing this interface , return
        if (capturingInterfaces.contains(interfaceName)){
            System.out.println("has started this interface");
            return;
        }
        List<PcapIf> alldevs = new ArrayList<>(); // Will be filled with
        // NICs
        StringBuilder errbuf = new StringBuilder(); // For any error msgs

        /*
         ***************************************************************************
         * First get a list of devices on this system
         *************************************************************************
         */
        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices, error is %s",
                    errbuf.toString());
            return;
        }
        /*
        System.out.println("Network devices found:");

        // 迭代找到的所有网卡，如果该网卡有介绍则输出介绍
        int i = 0;
        for (PcapIf device : alldevs) {
            String description = (device.getDescription() != null) ? device
                    .getDescription() : "No description available";
            System.out.printf("#%d: %s [%s]\n", i++, device.getName(),
                    description);
        }
        */
        /*
        PcapIf device = alldevs.get(i); // We know we have at least 1 device 选择监听那个网卡
        System.out.printf("\nChoosing '%s' on your behalf:\n",
                (device.getDescription() != null) ? device.getDescription()
                        : device.getName());
        System.out.println("choose ok!");
        */

        /***************************************************************************
         * Second we open up the selected device
         **************************************************************************/
        // 截取长度不超过数据报max65535
        int snaplen = 64 * 1024; // Capture all packets, no trucation 截断
        // 混杂模式
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
        int timeout = 10 * 1000; // 10 seconds in millis
        /*
        Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout,
                errbuf);
        System.out.println(String.format("has open %s",device.getName()));
        */
        Pcap pcap = Pcap.openLive(interfaceName, snaplen, flags, timeout,
                errbuf);
        if (pcap == null) {
            System.err.printf("Error while opening device for capture: "
                    + errbuf.toString());
            return;
        }

        synchronized (capturingInterfaces) {
            capturingInterfaces.add(interfaceName);
        }

        //PacketHandler处理
        /***************************************************************************
         * Fourth we enter the loop and tell it to capture 10 packets. The loop
         * method does a mapping of pcap.datalink() DLT value to JProtocol ID,
         * which is needed by JScanner. The scanner scans the packet buffer and
         * decodes the headers. The mapping is done automatically, although a
         * variation on the loop method exists that allows the programmer to
         * sepecify exactly which protocol ID to use as the data link type for
         * this pcap interface.
         **************************************************************************/
        String ofile = "tmp-capture-file.cap";
        PcapDumper dumper = pcap.dumpOpen(ofile); // output file
        packetHandler.setPcap(pcap);
        packetHandler.start();
        pcap.loop(-1, packetHandler, "jNetP_cap rocks!");

        /***************************************************************************
         * Last thing to do is close the pcap handle
         **************************************************************************/
        dumper.close(); //dumper 和 pcap都要关闭
        pcap.close();
        System.out.println(String.format("has close %s",interfaceName));
    }

    /**
     * 可以独立运行的程序，用于测试
     */
//    public static void mainCapture(String args[]) {
//        //if has started capturing this interface , return
//        List<PcapIf> alldevs = new ArrayList<>(); /* Will be filled with */
//        // NICs
//        StringBuilder errbuf = new StringBuilder(); // For any error msgs
//
//        /*
//         ***************************************************************************
//         * First get a list of devices on this system
//         *************************************************************************
//         */
//        int r = Pcap.findAllDevs(alldevs, errbuf);
//        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
//            System.err.printf("Can't read list of devices, error is %s",
//                    errbuf.toString());
//            return;
//        }
//
//        System.out.println("Network devices found:");
//        int i = 0;
//        // 迭代找到的所有网卡，如果该网卡有介绍则输出介绍
//        for (PcapIf device : alldevs) {
//            String description = (device.getDescription() != null) ? device
//                    .getDescription() : "No description available";
//            System.out.printf("#%d: %s [%s]\n", i++, device.getName(),
//                    description);
//        }
//
//        i = 2;
//        PcapIf device = alldevs.get(i); // We know we have at least 1 device 选择监听那个网卡
//        System.out.printf("\nChoosing '%s' on your behalf:\n",
//                (device.getDescription() != null) ? device.getDescription()
//                        : device.getName());
//        System.out.println("choose ok!");
//
//
//        /***************************************************************************
//         * Second we open up the selected device
//         **************************************************************************/
//        // 截取长度不超过数据报max65535
//        int snaplen = 64 * 1024; // Capture all packets, no trucation 截断
//        // 混杂模式
//        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
//        int timeout = 10 * 1000; // 10 seconds in millis
//        /*
//        Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout,
//                errbuf);
//        System.out.println(String.format("has open %s",device.getName()));
//        */
//        Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout,
//                errbuf);
//        if (pcap == null) {
//            System.err.printf("Error while opening device for capture: "
//                    + errbuf.toString());
//            return;
//        }
//
//        //PacketHandler处理
//        /***************************************************************************
//         * Fourth we enter the loop and tell it to capture 10 packets. The loop
//         * method does a mapping of pcap.datalink() DLT value to JProtocol ID,
//         * which is needed by JScanner. The scanner scans the packet buffer and
//         * decodes the headers. The mapping is done automatically, although a
//         * variation on the loop method exists that allows the programmer to
//         * sepecify exactly which protocol ID to use as the data link type for
//         * this pcap interface.
//         **************************************************************************/
//        String ofile = "tmp-capture-file.cap";
//        PcapDumper dumper = pcap.dumpOpen(ofile); // output file
//        PacketHandler<String> packetHandler = new PacketHandler<>();
//        packetHandler.start();
//        pcap.loop(-1, packetHandler, "jNetP_cap rocks!");
//
//        /***************************************************************************
//         * Last thing to do is close the pcap handle
//         **************************************************************************/
//        dumper.close(); //dumper 和 pcap都要关闭
//        pcap.close();
//        System.out.println(String.format("has close %s",device.getName()));
//    }

}

