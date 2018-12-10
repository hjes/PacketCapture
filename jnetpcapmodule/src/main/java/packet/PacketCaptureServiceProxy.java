package packet;

import common.PacketService;
import data.Data;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 *
 */
public class PacketCaptureServiceProxy{
    //String：网络端口，PackageSender：想该网络端口发送数据的发送器
    private static HashMap<String,PackageSender> senderPool = new HashMap<>(5);
    private static List<String> interfaceName;
    private static List<String> interfaceDetails;
    /**
     * @param interfaceName 接口名
     * @param data 数据
     * @return 数据是否添加到队列
     */
    public static void sendSomeData(String interfaceName,Data data) {
        senderPool.get(interfaceName).process(data.getPacket());
    }

    public static List<String> getAllInterfacesName() {
        if (interfaceName==null)
            interfaceName = getInfo(0);
        return interfaceName;
    }

    public static List<String> getAllInterfaceDetails(){
        if (interfaceDetails==null)
            interfaceDetails = getInfo(1);
        return interfaceDetails;
    }

    private static List<String> getInfo(int code){
        List<String> infos = new ArrayList<>(10);
        StringBuilder errbuf = new StringBuilder(); // For any error msgs
        List<PcapIf> alldevs = new ArrayList<>(); // Will be filled with
        int r = Pcap.findAllDevs(alldevs, errbuf);
        if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
            System.err.printf("Can't read list of devices, error is %s",
                    errbuf.toString());
            return null;
        }
        // 迭代找到的所有网卡
        //code = 0，获取名字
        //code = 1，获取所有网卡的具体信息
        if (code==0) {
            for (PcapIf device : alldevs) {
                infos.add(device.getName());
            }
        }else{
            for (PcapIf device : alldevs) {
                infos.add(String.format("deviceName : %s\ndescription : %s\naddresses : %s", device.getName(), device.getDescription(), device.getAddresses()));
            }
        }
        return infos;
    }

    /**
     * 根据接口名获得转发工具
     * @param interfaceName 接口名字
     * @return  转发工具
     */
    public static PackageSender getPacketSender(String interfaceName) {
        PackageSender packageSender;
        if ((packageSender = senderPool.get(interfaceName))!=null){
            return packageSender;
        }
        packageSender = new PackageSender(interfaceName);
        senderPool.put(interfaceName,packageSender);
        return packageSender;
    }

    public static void startCapturingPacket(String interfaceName){
        PackageCapture.mainCapture(interfaceName);
    }

}