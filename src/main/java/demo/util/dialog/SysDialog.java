package demo.util.dialog;

import demo.model.SysInfoBean;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class SysDialog {
    private static Label threadNumberLabel,heapUsage,heapMax,nonHeapUsage,nonHeapMax;
    private static Popup popup;

    public static void showSysInfoDialog(Stage stage, double x, double y,
                                         SysInfoBean sysInfoBean){
        if (popup!=null){
            threadNumberLabel.setText(String.valueOf(sysInfoBean.getThreadNum()));
            heapUsage.setText(String.valueOf(sysInfoBean.getMemoryInfo().getHeadUsage()));
            heapMax.setText(String.valueOf(sysInfoBean.getMemoryInfo().getHeadMax()));
            nonHeapUsage.setText(String.valueOf(sysInfoBean.getMemoryInfo().getNonHeapUsage()));
            nonHeapMax.setText(String.valueOf(sysInfoBean.getMemoryInfo().getNonHeapMax()));
            popup.setAnchorX(x);
            popup.setAnchorY(y);
            popup.show(stage);
            return;
        }
        popup = new Popup();
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-border-color: BLACK;-fx-padding: 20;-fx-background-color: WHITE");
        gridPane.setPrefSize(200,100);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        Text text = new Text("线程数");
        gridPane.add(text,0,0);
        threadNumberLabel = new Label();
        threadNumberLabel.setText(String.valueOf(sysInfoBean.getThreadNum()));
        gridPane.add(threadNumberLabel,1,0);
        Text text1 = new Text("堆用量");
        heapUsage = new Label(String.valueOf(sysInfoBean.getMemoryInfo().getHeadUsage()));
        gridPane.add(text1,0,1);
        gridPane.add(heapUsage,1,1);
        Text text2 = new Text("堆总量");
        heapMax = new Label(String.valueOf(sysInfoBean.getMemoryInfo().getHeadMax()));
        gridPane.add(text2,0,2);
        gridPane.add(heapMax,1,2);
        Text text3 = new Text("非堆用量");
        nonHeapUsage = new Label(String.valueOf(sysInfoBean.getMemoryInfo().getNonHeapUsage()));
        gridPane.add(text3,0,3);
        gridPane.add(nonHeapUsage,1,3);
        Text text4 = new Text("非堆总量");
        nonHeapMax = new Label(String.valueOf(sysInfoBean.getMemoryInfo().getNonHeapMax()));
        gridPane.add(text4,0,4);
        gridPane.add(nonHeapMax,1,4);
        popup.getContent().add(gridPane);
        popup.setAnchorX(x);
        popup.setAnchorY(y);
        gridPane.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popup.hide();
            }
        });
        popup.show(stage);
    }
}
