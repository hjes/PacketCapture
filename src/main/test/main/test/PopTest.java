package main.test;

import demo.model.SysInfoBean;
import javafx.application.Application;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Test;


public class PopTest extends Application {
    ObservableList<SysInfoBean> sysInfoBeans;
    ObservableObjectValue<SysInfoBean> sysInfoBeanObservableObjectValue;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JFX Popup Demo");
        primaryStage.setResizable(false);
        StackPane stackPane = new StackPane();
        Button button = new Button("click");
        stackPane.getChildren().add(button);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
        Scene scene = new Scene(stackPane,500,500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Test
    public void dialogTest(){
    }
}
