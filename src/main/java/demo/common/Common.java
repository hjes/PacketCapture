package demo.common;

import demo.model.SysInfoBean;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class Common {
    //日志事件注册
    public static final String LOGGING = "logging";

    //获取当前程序的占用信息
    public static SysInfoBean getSysInfo(){
        SysInfoBean sysInfoBean = new SysInfoBean();
        sysInfoBean.setThreadNum(Thread.activeCount());
        //用于获取堆内存的使用情况和非堆内存的使用情况
        MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
        SysInfoBean.MemoryInfo memoryInfo = new SysInfoBean.MemoryInfo();
        memoryInfo.setHeadUsage((long)(bean.getHeapMemoryUsage().getUsed()/1024.0/1024.0));
        memoryInfo.setHeadMax((long)(bean.getHeapMemoryUsage().getMax()/1024.0/1024.0));
        memoryInfo.setNonHeapMax((long)(bean.getNonHeapMemoryUsage().getMax()/1024.0/1024.0));
        memoryInfo.setNonHeapUsage((long)(bean.getNonHeapMemoryUsage().getUsed()/1024.0/1024.0));
        sysInfoBean.setMemoryInfo(memoryInfo);
        return sysInfoBean;
    }


    //TODO Java动态运行代码 从class文件中加载filter和process

}
