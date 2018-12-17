package packet.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;


public class SpinnerUtils {

    private static HashMap<Pane, JFXSpinner> paneProgressBarHashMap = new HashMap<>(5);
    public static void showSpinner(Pane pane){
        JFXSpinner jfxSpinner = null;
        if ((jfxSpinner = paneProgressBarHashMap.get(pane))!=null){
            pane.getChildren().add(jfxSpinner);
        }else{
            jfxSpinner = new JFXSpinner();
            pane.getChildren().add(jfxSpinner);
            paneProgressBarHashMap.put(pane,jfxSpinner);
        }
    }

    public static void dismissSpinner(Pane pane){
        if(paneProgressBarHashMap.get(pane)!=null){
            Platform.runLater(()->pane.getChildren().remove(paneProgressBarHashMap.get(pane)));
        }
    }
}
