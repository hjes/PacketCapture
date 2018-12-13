package demo.util.dialog;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class SenderDialog {

    private static Popup dialogPop;
    public void showSysInfoDialog(Stage stage) throws IOException {
        if (dialogPop!=null){
            dialogPop.show(stage);
            return;
        }
        dialogPop = new Popup();
        BorderPane borderPane = FXMLLoader.load(getClass().getClassLoader().getResource("views/dialog/sender_dialog.fxml"));
        Label close = (Label) borderPane.lookup("#close");
        System.out.println(close);
        close.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialogPop.hide();
            }
        });
        dialogPop.requestFocus();

        dialogPop.setHideOnEscape(true);
        dialogPop.getContent().add(borderPane);
        dialogPop.show(stage);
    }
}
