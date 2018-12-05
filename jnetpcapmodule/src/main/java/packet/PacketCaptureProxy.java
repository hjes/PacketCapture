package packet;

import data.Data;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PacketCaptureProxy {
    private Executor executor = Executors.newCachedThreadPool();
    //String：网络端口，PackageSender：想该网络端口发送数据的发送器
    private static HashMap<String,PackageSender> senderPool = new HashMap<>(5);
    public PackageSender getPackageSender(String interfaceName){
        PackageSender packageSender;
        if ((packageSender = senderPool.get(interfaceName))!=null){
            return packageSender;
        }
        packageSender = new PackageSender(interfaceName);
        senderPool.put(interfaceName,packageSender);
        return packageSender;
    }

    /**
     * @param interfaceName 接口名
     * @param data 数据
     * @return 数据是否添加到队列
     */
    public static boolean sendSomeMessage(String interfaceName, Data data){
        senderPool.get(interfaceName).addPacket(data.getPacket());
    }
}
