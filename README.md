# JavaFx Packet Capture Demo App


Packet过滤器的设计：
1、接口PacketFilter；
2、多个接口实现，IPFilter、TCPFilter等；<这里需要注意Filter的重复性>
3、一个线程抓取一个接口的包，然后另外开一个线程对这些包进行处理；包过滤是放在抓包线程中的而不是放在处理包的线程中。
一般过滤器添加是在初始化的时候，所以暂时不用考虑MultiPacketFilter类过滤器多线程添加数据一致性问题。

Packet转发器的设计：
1、转发属于process，放在PacketProcess接口方法中实现；
2、转发器面向设备接口，即一个设备对应一个转发器，具体实现在PacketProxy的HashMap中；
3、