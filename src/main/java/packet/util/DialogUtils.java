package packet.util;

import packet.model.SysInfoBean;
import packet.util.dialog.SysDialog;
import javafx.stage.Stage;

public class DialogUtils {

    public static void showSysInfoDialog(Stage stage, double x, double y, SysInfoBean sysInfoBean){
        SysDialog.showSysInfoDialog(stage,x,y,sysInfoBean);
    }

}
