package packet.util.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.HashMap;

import static packet.util.graph.GraphMethod.sameMethod;

public class AreaChartPane extends StackPane implements LineGraph{

    private AreaChart<String,Number> areaChart;
    //interface -> seriesData
    private static HashMap<String, ObservableList<XYChart.Data<String,Number>>> areaMap =
            new HashMap<>(5);

    @Override
    public void addSeries(String interfaceName) {
        //往表格中添加数据项目
        sameMethod(interfaceName, areaChart.getData(), areaMap);
    }


    @Override
    public void init(String title, String xLabel, String yLabel){
        final CategoryAxis xAxis;
        xAxis = new CategoryAxis();
        final NumberAxis yAxis;
        yAxis = new NumberAxis();
        areaChart = new AreaChart<>(xAxis,yAxis);
        areaChart.setTitle(title);
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
        //将初始化好的表格添加到Pane中
        getChildren().add(areaChart);
    }


    /**
     * 添加数据到接口对应的数据项中，首先要addSeries
     * @param interfaceName 接口名
     * @param data 具体的数据
     */
    public static void addData(String interfaceName, XYChart.Data<String,Number> data){
        GraphMethod.addData(interfaceName, data, areaMap);
    }

}
