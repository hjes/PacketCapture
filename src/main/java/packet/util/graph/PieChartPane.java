package packet.util.graph;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;

import java.util.List;

public class PieChartPane extends StackPane{

    private PieChart pieChart;

    public void init(String chartTitle, List<PieChart.Data> pieChartData){
        ObservableList<PieChart.Data> pieChartDataObserver =
                FXCollections.observableArrayList(pieChartData);
        pieChart= new PieChart(pieChartDataObserver);
        pieChart.setTitle(chartTitle);
        getChildren().add(pieChart);
    }

    public void setPieChart(double labelLineWidth, Side labelLegendSide){
        pieChart.setLabelLineLength(labelLineWidth);
        pieChart.setLegendSide(labelLegendSide);
    }

}
