package demo.util;

import demo.model.SysInfoBean;
import demo.util.dialog.SysDialog;
import javafx.stage.Stage;

public class DialogUtils {

    public static void showSysInfoDialog(Stage stage, double x, double y, SysInfoBean sysInfoBean){
        SysDialog.showSysInfoDialog(stage,x,y,sysInfoBean);
    }

}
