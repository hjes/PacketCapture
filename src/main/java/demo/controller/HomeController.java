package demo.controller;

import com.jfoenix.controls.JFXButton;
import demo.util.NotificationUtils;
import io.datafx.controller.ViewController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import javax.annotation.PostConstruct;

/**
 * Created by Snart Lu on 2018/2/5.
 */
@ViewController(value = "/views/home.fxml")
public class HomeController {

    @FXML private JFXButton homeBtn1;
    @FXML private BorderPane root;

    @PostConstruct
    private void init(){
        homeBtn1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("you click btn1");
                NotificationUtils.notifySuccess("oh kaqima",root);
            }
        });
    }
}
