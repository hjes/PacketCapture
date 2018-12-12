package packet;

/**
 * 抓包线程
 */
public class PacketCaptureThread extends Thread{
    //抓包的接口名
    private String interfaceName;
    //Handler
    private PacketHandler<String> packetHandler = new PacketHandler<>();
    //
    private boolean hasStart = false;
    private boolean hasPause = false;

    public PacketCaptureThread(String interfaceName){
        this.interfaceName = interfaceName;
    }

    @Override
    public void run() {
        PacketCapture.mainCapture(interfaceName,packetHandler);
    }

    public void pause(){
        if (!hasPause) {
            packetHandler.setPause();
            hasPause = true;
        }
    }

    public void consume(){
        if (hasPause) {
            packetHandler.setConsume();
        }
        hasPause = false;
    }

    public void startService(){
        hasStart = true;
        super.start();
    }
}
