package main.test;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.controls.JFXRippler.RipplerMask;
import com.jfoenix.controls.JFXRippler.RipplerPos;
import graph.GraphStage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import packet.util.SpinnerUtils;
import packet.util.graph.AreaChartPane;
import packet.util.graph.BarChartPane;
import packet.util.graph.LineChartPane;

import javax.swing.*;
import java.util.Date;
import java.util.Random;

public class PopupDemo extends Application {

    int i = 0;
    private Random random = new Random();

    @Override
    public void start(Stage primaryStage) throws Exception {

        JFXHamburger show = new JFXHamburger();
        show.setPadding(new Insets(10, 5, 10, 5));
        JFXRippler rippler = new JFXRippler(show, RipplerMask.CIRCLE, RipplerPos.BACK);

        JFXListView<Label> list = new JFXListView<>();
        for (int i = 1; i < 5; i++) {
            list.getItems().add(new Label("Item " + i));
        }

        JFXButton jfxButton = new JFXButton("new stage");
        jfxButton.setButtonType(JFXButton.ButtonType.RAISED);
        jfxButton.setOnMouseClicked(value->{
            GraphStage secondStage = new GraphStage("1",new GraphStage.GraphInitData(
                    "抓包数",
                    "时间",
                    "数目",
                    "攻击数目",
                    "时间",
                    "数目",
                    new String[]{"interfaceOne"},
                    new String[]{"from NodeOne"}
            ));
            try {
                secondStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(()->{
                        i+=random.nextInt(5);
                        LineChartPane.addData("interfaceOne",new XYChart.Data<>(String.valueOf(
                                new Date().getSeconds()
                        ), i));
                        i+=random.nextInt(5);
                        BarChartPane.addData("from NodeOne",new XYChart.Data<>(String.valueOf(
                                new Date().getSeconds()),i));
                        i = 0;
                    });
                }
            }
        }).start();

        AnchorPane container = new AnchorPane();
        container.getChildren().add(rippler);
        AnchorPane.setLeftAnchor(rippler, 200.0);
        AnchorPane.setTopAnchor(rippler, 210.0);

        AnchorPane.setLeftAnchor(jfxButton,50.0);
        AnchorPane.setTopAnchor(jfxButton,50.0);
        container.getChildren().add(jfxButton);

        StackPane main = new StackPane();
        main.getChildren().addAll(container);


        JFXPopup popup = new JFXPopup(list);
        rippler.setOnMouseClicked(e -> popup.show(rippler, PopupVPosition.BOTTOM, PopupHPosition.RIGHT));

        final Scene scene = new Scene(main, 800, 800);

        primaryStage.setTitle("JFX Popup Demo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
