package filter;

import common.ObserverCenter;
import org.jnetpcap.packet.PcapPacket;

import java.util.*;

public class MultiPacketFilter implements PacketFilter {

    //TODO 有没有可能多线程往这里面加过滤器
    private LinkedList<PacketFilter> packetFilterLinkedList =  new LinkedList<>();

    private HashMap<String,PacketFilter> filterHashMap = new HashMap<>();

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

    public void addPacketFilter(String filterName){
        addPacketFilters(new String[]{filterName});
    }

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

    public LinkedList<PacketFilter> getAllFilters(){
        return packetFilterLinkedList;
    }

    public void addAll(MultiPacketFilter multiPacketFilter){
        for (PacketFilter packetFilter:multiPacketFilter.getAllFilters()){
            if (!filterHashMap.containsKey(packetFilter.getFilterName())) {
                packetFilterLinkedList.add(packetFilter);
                filterHashMap.put(packetFilter.getFilterName(),packetFilter);
            }
        }
    }

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
