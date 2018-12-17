package graph;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import packet.util.graph.BarChartPane;
import packet.util.graph.LineChartPane;

import java.io.IOException;
import java.util.Objects;

public class GraphStage extends Stage {

    private LineChartPane lineChartPane;
    private BarChartPane barChartPane;

    public GraphStage(String title,GraphInitData graphInitData){
        setTitle(title);
        BorderPane pane = null;
        try {
            pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/others/graph_stage.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert pane != null;
        setScene(new Scene(pane));
        setWidth(pane.getPrefWidth());
        setHeight(pane.getPrefHeight());
        setAlwaysOnTop(true);
        setFullScreen(true);

        lineChartPane = (LineChartPane) pane.lookup("#graph_stage_line_chart");
        barChartPane = (BarChartPane) pane.lookup("#graph_stage_bar_chart");
        lineChartPane.init(graphInitData.getLineChartTitle(),
                            graphInitData.getLineChartXLabel(),
                            graphInitData.getLineChartYLabel());
        barChartPane.init(graphInitData.getBarChartTitle(),
                          graphInitData.getBarChartXLabel(),
                          graphInitData.getBarChartYLabel());
        for (String string:graphInitData.getLineChartSeries())
            lineChartPane.addSeries(string);
        for (String string:graphInitData.getBarChartSeries())
            barChartPane.addSeries(string);
    }

    public static class GraphInitData{
        private String lineChartTitle;
        private String lineChartXLabel;
        private String lineChartYLabel;
        private String[] lineChartSeries;

        private String barChartTitle;
        private String barChartXLabel;
        private String barChartYLabel;
        private String[] barChartSeries;

        public GraphInitData(String lineChartTitle, String lineChartXLabel, String lineChartYLabel, String barChartTitle, String barChartXLabel, String barChartYLabel
        ,String[] lineChartSeries,String[] barChartSeries) {
            this.lineChartTitle = lineChartTitle;
            this.lineChartXLabel = lineChartXLabel;
            this.lineChartYLabel = lineChartYLabel;
            this.barChartTitle = barChartTitle;
            this.barChartXLabel = barChartXLabel;
            this.barChartYLabel = barChartYLabel;
            this.lineChartSeries = lineChartSeries;
            this.barChartSeries = barChartSeries;
        }

        public String getLineChartTitle() {
            return lineChartTitle;
        }

        public String getLineChartXLabel() {
            return lineChartXLabel;
        }

        public String getLineChartYLabel() {
            return lineChartYLabel;
        }

        public String getBarChartTitle() {
            return barChartTitle;
        }

        public String getBarChartXLabel() {
            return barChartXLabel;
        }

        public String getBarChartYLabel() {
            return barChartYLabel;
        }

        public String[] getLineChartSeries() {
            return lineChartSeries;
        }

        public String[] getBarChartSeries() {
            return barChartSeries;
        }
    }

}
