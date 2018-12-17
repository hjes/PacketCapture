package packet.util.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphMethod {
    static void sameMethod(String interfaceName, ObservableList<XYChart.Series<String, Number>> data, HashMap<String, ObservableList<XYChart.Data<String, Number>>> areaMap) {
        if (areaMap.containsKey(interfaceName)){
            System.err.println("contain " + interfaceName);
            return;
        }
        ObservableList<XYChart.Data<String,Number>> observableList = FXCollections.observableList(new ArrayList<>());
        XYChart.Series<String,Number> stringNumberSeries = new XYChart.Series<>(observableList);
        stringNumberSeries.setName(interfaceName);
        data.add(stringNumberSeries);
        areaMap.put(interfaceName,observableList);
    }

    static void addData(String interfaceName, XYChart.Data<String, Number> data, HashMap<String, ObservableList<XYChart.Data<String, Number>>> barMap) {
        ObservableList<XYChart.Data<String,Number>> observableList;
        if ((observableList = barMap.get(interfaceName))!=null){
            observableList.add(data);
        }else{
            System.err.println("can not find the series to " + interfaceName);
        }
    }
}
