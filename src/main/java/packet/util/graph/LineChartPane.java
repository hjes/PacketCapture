package packet.util.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;
import java.util.HashMap;

public class LineChartPane extends StackPane implements LineGraph{


    private LineChart<String,Number> lineChart;

    //interface -> seriesData
    private static HashMap<String, ObservableList<XYChart.Data<String,Number>>> seriesHashMap =
            new HashMap<>(5);


    public void init(String title,String xLabel,String yLabel){
        //初始化LineChart
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis,yAxis);
        lineChart.setTitle(title);
        xAxis.setLabel(xLabel);
        yAxis.setLabel(yLabel);
        //将初始化好的表格添加到Pane中
        getChildren().add(lineChart);
    }

    public void addSeries(String interfaceName){
        //往表格中添加数据项目
        GraphMethod.sameMethod(interfaceName, lineChart.getData(), seriesHashMap);
    }

    /**
     * 添加数据到接口对应的数据项中，首先要addSeries
     * @param interfaceName 接口名
     * @param data 具体的数据
     */
    public static void addData(String interfaceName,XYChart.Data<String,Number> data){
        GraphMethod.addData(interfaceName,data,seriesHashMap);
    }

}
