package demo.util;

import common.ObserverCenter;
import data.PacketEntity;
import demo.model.PacketModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Pair;
import org.jnetpcap.packet.PcapPacket;
import packet.PacketCaptureServiceProxy;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AlertUtils {
    private static Alert initDialog(String title, String headText, String contentText, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headText);
        alert.setContentText(contentText);
        return alert;
    }

    public static void showStandardDialog(String title, String headText, String contentText){
        Alert alert = initDialog(title,headText,contentText, Alert.AlertType.NONE);
        alert.show();
    }

    public static void showWaring(String headText,String contentText){
        Alert alert = initDialog("Warning",headText,contentText, Alert.AlertType.WARNING);
        alert.show();
    }

    public static void showSenderWin(PacketModel packetModel,List<String> interfaceNames,PcapPacket packet){
        Alert alert = initDialog("Send Packet",null,null, Alert.AlertType.CONFIRMATION);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPrefSize(200,200);
        gridPane.setPadding(new Insets(10,5,10,5));
        Label ID = new Label("packet_id");
        ID.setAlignment(Pos.CENTER);
        Label packetId = new Label(String.valueOf(packetModel.idProperty()));
        gridPane.add(ID,0,0);
        gridPane.add(packetId,0,1);
        ChoiceBox<String> stringChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(interfaceNames));
        stringChoiceBox.setTooltip(new Tooltip("选择接口"));
        stringChoiceBox.setStyle("-fx-alignment: CENTER");
        gridPane.add(stringChoiceBox,0,1,2,1);
        Button btnConfirm = new Button("confirm");
        btnConfirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (stringChoiceBox.getValue()==null)
                    return;
                //
                ObserverCenter.notifyLogging("请求发送数据");
                PacketCaptureServiceProxy.sendSomeData(stringChoiceBox.getValue(), new PacketEntity(packet));
                alert.close();
            }
        });

        gridPane.add(btnConfirm,0,2,2,2);
        alert.getDialogPane().setContent(gridPane);
        alert.show();
    }

    /**
     * 显示异常
     * @param ex 异常类
     */
    public static void showException(Exception ex){
        Alert alert = initDialog("Exception",ex.getMessage(),ex.getLocalizedMessage(), Alert.AlertType.ERROR);
        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }


    public static void showChoiceDialog(String title, String headText, List<String> choices,Callback<String> stringCallback){
        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle(title);
        dialog.setHeaderText(headText);
        dialog.setResizable(true);
        Optional<String> result = dialog.showAndWait();
        //Lambda
        result.ifPresent(new Consumer<String>() {
            @Override
            public void accept(String s) {
                stringCallback.callback(s);
            }
        });
    }

    public static void showTextInputDialog(String title,String headText,String contentText,Callback<String> stringCallback){
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter your name:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(new Consumer<String>() {
            @Override
            public void accept(String s) {
                stringCallback.callback(s);
            }
        });
        // The Java 8 way to get the response value (with lambda expression).
        //result.ifPresent(name -> System.out.println("Your name: " + name));
    }

    public static <T> void showCustomLoginDialog(String title,String headText,String contentText,Callback<T> callback){
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Look, a Custom Login Dialog");

        // Set the icon (must be included in the project).
        //dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(username::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait().filter(new Predicate<Pair<String, String>>() {
            @Override
            public boolean test(Pair<String, String> stringStringPair) {
                if (stringStringPair.getKey().equals("hongqianhui")){
                    System.out.print("not match");
                    return false;
                }
                return true;
            }
        });

        result.ifPresent(usernamePassword -> {
            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
        });
    }


    public interface Callback<T>{
        void callback(T t);
    }

}
