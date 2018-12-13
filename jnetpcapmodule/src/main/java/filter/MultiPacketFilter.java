package filter;

import common.ObserverCenter;
import org.jnetpcap.packet.PcapPacket;

import java.util.*;

public class MultiPacketFilter implements PacketFilter {

    //TODO 有没有可能多线程往这里面加过滤器
    private LinkedList<PacketFilter> packetFilterLinkedList =  new LinkedList<>();

    //过滤器名字和具体过滤器的对应关系
    private HashMap<String,PacketFilter> filterHashMap = new HashMap<>();

    /**
     * 添加多个filter
     * @param filterNames 过滤器的名字集合
     */
    public void addPacketFilters(String[] filterNames){
        PacketFilter packetFilter = null;
        for (String filterName:filterNames){
            if ((packetFilter = getTheCorrectFilter(filterName))!=null){
                if (filterHashMap.get(filterName)==null) {
                    packetFilterLinkedList.add(packetFilter);
                    filterHashMap.put(filterName,packetFilter);
                }else{
                    ObserverCenter.notifyLogging("has added the FILTER : " + filterName);
                }
            }
        }
    }

    /**
     * 添加单个过滤器
     * @param filterName 过滤器的名字
     */
    public void addPacketFilter(String filterName){
        addPacketFilters(new String[]{filterName});
    }

    /**
     * 获取当前所有过滤器的名字集合
     * @return
     */
    public String[] getAllFilterName(){
        if (filterHashMap==null)
            return new String[0];
        Set<String> set = filterHashMap.keySet();
        String[] strings = new String[set.size()];
        int i = 0;
        for (String string:set){
            strings[i] = string;
            i++;
        }
        return strings;
    }

    /**
     * 获得所有的过滤器
     * @return
     */
    public LinkedList<PacketFilter> getAllFilters(){
        return packetFilterLinkedList;
    }

    /**
     * 根据前一个多过滤器添加到一个新的多过滤器中
     * @param multiPacketFilter
     */
    public void addAll(MultiPacketFilter multiPacketFilter){
        for (PacketFilter packetFilter:multiPacketFilter.getAllFilters()){
            if (!filterHashMap.containsKey(packetFilter.getFilterName())) {
                packetFilterLinkedList.add(packetFilter);
                filterHashMap.put(packetFilter.getFilterName(),packetFilter);
            }
        }
    }

    /**
     * 根据名字获得过滤器
     * @param filterName
     * @return
     */
    private PacketFilter getTheCorrectFilter(String filterName){
        PacketFilter packetFilter = null;
        boolean hasAddedTheFilter = true;
        switch (filterName){
            case "TCP":
                packetFilter =  new TcpFilter();break;
            case "IP":
                packetFilter = new IpFilter();break;
            case "UDP":
                packetFilter = new UdpFilter();break;
            case "HTTP":
                packetFilter = new HttpFilter();break;
            default:
                hasAddedTheFilter = false;
                break;
        }
        if (hasAddedTheFilter){
            ObserverCenter.notifyLogging("add the FILTER : " + filterName);
        }else{
            ObserverCenter.notifyLogging("can not find the FILTER : " + filterName);
        }
        return packetFilter;
    }

    /**
     * 移除过滤器
     * @param removeFilterList
     */
    public void removeFilter(List<String> removeFilterList){
        for (String string:removeFilterList){
            packetFilterLinkedList.remove(filterHashMap.get(string));
            filterHashMap.remove(string);
        }
    }

    @Override
    public boolean packetFilter(PcapPacket packet) {
        for (PacketFilter packetFilter:packetFilterLinkedList){
            if (!packetFilter.packetFilter(packet))
                return false;
        }
        return true;
    }

    @Override
    public String getFilterName() {
        return FilterCommon.MULTI_FILTER;
    }

}
