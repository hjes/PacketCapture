package main;


import common.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ServiceCenter {

    private ServiceCenter(){}

    private HashMap<String, Service> serviceMap = new HashMap<String, Service>(5);
    /**
     * 注册服务
     * @param service 服务实例
     */
    public boolean registerService(Service service){
        return registerService(service,false);
    }

    /**
     * 判断是否有旧的重名服务，如果有的话判断是否要覆盖
     * @param service 服务实例
     * @param coverOldService 是否要覆盖已有的旧服务
     * @return 是否添加服务成功
     */
    public boolean registerService(Service service,boolean coverOldService){
        if (coverOldService){
            serviceMap.put(service.serviceName(),service);
            return true;
        }
        if (serviceMap.get(service.serviceName())==null){
            serviceMap.put(service.serviceName(),service);
            return true;
        }
        return false;

    }

    /**
     * @return 所有服务名字
     */
    public String[] getAllServiceName(){
        int i = 0;
        String serviceName[];
        Set<Map.Entry<String,Service>> entrySet = serviceMap.entrySet();
        serviceName = new String[entrySet.size()];
        for (Map.Entry<String, Service> entry:entrySet){
            serviceName[i] = entry.getKey();
            i++;
        }
        return serviceName;
    }

    /**
     * 根据服务名称发现具体服务
     * @param serviceName 服务名称
     * @return
     */
    public Service findService(String serviceName){
        return serviceMap.get(serviceName);
    }

    public Service findService(Class<?> clazz){
        return serviceMap.get(clazz.getName());
    }

    private static class ServiceCenterHolder{
        private static ServiceCenter INSTANCE = new ServiceCenter();
    }

    public static ServiceCenter getInstance(){
        return ServiceCenterHolder.INSTANCE;
    }
}
