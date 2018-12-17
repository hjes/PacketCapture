package packet.util.graph;

import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;

import java.util.HashMap;

import static packet.util.graph.GraphMethod.sameMethod;

public class BarChartPane extends StackPane implements LineGraph{

    private BarChart<String,Number> barChart;
    //interface -> seriesData
    private static HashMap<String, ObservableList<XYChart.Data<String,Number>>> barMap =
            new HashMap<>(5);

    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();

    @Override
    public void init(String title, String xLabel, String yLabel) {
        barChart = new BarChart<>(xAxis,yAxis);
        barChart.setTitle(title);
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);

        getChildren().add(barChart);
    }

    public void setTickLableRotation(int distance){
        xAxis.setTickLabelRotation(distance);
    }

    @Override
    public void addSeries(String interfaceName) {
        sameMethod(interfaceName, barChart.getData(), barMap);
    }

    /**
     * 添加数据到接口对应的数据项中，首先要addSeries
     * @param interfaceName 接口名
     * @param data 具体的数据
     */
    public static void addData(String interfaceName, XYChart.Data<String,Number> data){
       GraphMethod.addData(interfaceName, data, barMap);
    }


}
