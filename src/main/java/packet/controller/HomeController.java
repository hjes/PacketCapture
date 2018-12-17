package packet.controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTreeTableColumn;
import common.Common;
import common.ObserverCenter;
import data.PacketWrapper;
import org.jnetpcap.packet.JPacket;
import packet.App;
import packet.model.PacketTableProcessModel;
import packet.util.DialogUtils;
import packet.util.menu.GlobalMenu;
import packet.model.PacketModel;
import packet.mvc.AbstractController;
import packet.repository.HomeRepository;
import packet.util.AlertUtils;
import packet.util.menu.MenuCallback;
import packet.util.menu.TempMenu;
import io.datafx.controller.ViewController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.jnetpcap.packet.PcapPacket;
import packet.PacketCaptureServiceProxy;


import java.util.*;
import java.util.function.Function;

import static common.Common.CaptureThreadState.CONSUME;
import static common.Common.CaptureThreadState.WAITING;
import static common.Common.PACKET_LOSE_EVENT;

/**
 * Created by Snart Lu on 2018/2/5.
 */
@ViewController(value = "/views/home.fxml")
public class HomeController extends AbstractController<HomeRepository> {

    @FXML private BorderPane root;
    @FXML private Button home_btn_start_capturing;
    @FXML private JFXListView<String> home_list_interface;

    /**
     * table settings
     */
    @FXML
    private TableView<PacketModel> home_table_packet;
    @FXML
    private TableColumn<PacketModel, String> packetIDColumn;
    @FXML
    private TableColumn<PacketModel, String> packetLengthColumn;
    @FXML
    private TableColumn<PacketModel, String> packetProtocolColumn;
    @FXML
    private TableColumn<PacketModel,String> packetTimeColumn;
    @FXML
    private TableColumn<PacketModel,String> packetInfoColumn;
    @FXML
    private Button home_btn_lose_rate;
    @FXML
    private TextArea home_textarea_logging;
    @FXML
    private Button home_btn_flash;
    @FXML
    private CheckBox home_ckbox_auto_flash;
    @FXML
    private Button home_btn_clear_textarea;
    @FXML
    private StackPane home_img_notification;
    @FXML
    private Button home_btn_stop_thread;
    @FXML
    private ChoiceBox<String> home_choice_box_receive_interface;
    @FXML
    private TextArea home_textarea_packet_overview;

    //table 内容
    private ObservableList<PacketModel> dummyData;
    //当前停留的接口
    private String currentChosenInterface;
    //
    private List<String> hasOpenInterfaceName = new ArrayList<>(5);
    //
    private ObservableList<String> nameList;
    //
    private JPacket currentPcapPacket;


    @Override
    public void init(){
        //
        ObserverCenter.register("logging", s -> Platform.runLater(() -> home_textarea_logging.appendText("\n" + s)));
        ObserverCenter.notifyLogging("正在初始化...");
        //接口名字
        nameList = FXCollections.observableList(PacketCaptureServiceProxy.getAllInterfacesName());
        //ObservableList<String> nameList = FXCollections.observableList(Arrays.asList("1","2","3"));
        home_list_interface.setItems(nameList);
        //old_val表示上一次选中的对象，new_val表示新选中的，ov表示本次选中的
        //可以设置监听index或者content
        home_list_interface.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_val,
                 String new_val) -> {
                    ObserverCenter.notifyLogging("choose interface : " + ov.getValue());
                   currentChosenInterface = ov.getValue();
                   //如果该接口已经打开
                   if (hasOpenInterfaceName.contains(currentChosenInterface)){
                       switch (PacketCaptureServiceProxy.getThreadState(currentChosenInterface)){
                           case Common.CaptureThreadState.CONSUME:home_btn_start_capturing.setText("RUNNING");break;
                           case Common.CaptureThreadState.PAUSE:home_btn_start_capturing.setText("PAUSE");break;
                       }
                   }else{
                       home_btn_start_capturing.setText("START");
                   }
                });

        //TODO TempMenu优化
        home_list_interface.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY){
                    TempMenu.getInstance(currentChosenInterface).init(Arrays.asList("details","sender"))
                            .addListener(new MenuCallback() {
                        @Override
                        public void callback(String menuId, Object t) {
                            switch (menuId){
                                case "details":
                                    for (String infoRs:PacketCaptureServiceProxy.getAllInterfaceDetails()){
                                        System.out.println(infoRs);
                                    }
                                    break;
                                case "sender":
                                    AlertUtils.showMyAlert("发送设置",currentChosenInterface);
                                    break;
                            }
                        }
                    }).show(home_list_interface,event.getScreenX(),event.getScreenY());
                }else{
                    if (currentChosenInterface!=null){
                        TempMenu.getInstance(currentChosenInterface).hide();
                    }
                }
            }
        });

        //开始抓包
        home_btn_start_capturing.setOnMouseClicked(event -> {
            if (currentChosenInterface==null) {
                AlertUtils.showWaring("警告", "没有选择任何的接口");
                return;
            }
            if (!hasOpenInterfaceName.contains(currentChosenInterface)) {
                //TODO 打开设备
                ObserverCenter.notifyLogging("is opening interface : " + currentChosenInterface);
                startCapture(currentChosenInterface);
                ObserverCenter.notifyLogging("has open interface : " + currentChosenInterface );
                System.out.println("has open device : " + currentChosenInterface);
                home_btn_start_capturing.setText("RUNNING");
                hasOpenInterfaceName.add(currentChosenInterface);
                //抓包到主界面更新，抓包成功后回调该接口
                PacketCaptureServiceProxy.addProcessor(currentChosenInterface, new PacketTableProcessModel(
                        //这个地方如果当前的接口还没有创建表关联，会在get方法中创建然后返回实例
                        packet.common.Common.getTableDataByInterfaceName(currentChosenInterface).getPacketModelListViewModel()
                ,currentChosenInterface) {
                    @Override
                    public void process(PacketWrapper packetWrapper) {
                        //TODO 链接
                        threadOwnerModel.add(new PacketModel(String.valueOf(packetID),
                                ((Date)packetWrapper.getObject(PacketWrapper.TIME)).toString(),
                                packetWrapper.getPcapPacket(),null));
                        packetID++;
                        if (packetID%100==0&&packetID!=0)
                            ObserverCenter.notifyLogging(interfaceName + "has capture : " + packetID);

                        //PacketCaptureServiceProxy.getPacketSender(nameList.get(1)).sendDatas(packetWrapper.getPcapPacket());
                    }
                });
            }else{
                switch (PacketCaptureServiceProxy.getThreadState(currentChosenInterface)){
                    case CONSUME:PacketCaptureServiceProxy.setPacketCaptureThreadState(currentChosenInterface
                    ,Common.CaptureThreadState.PAUSE);
                    home_btn_start_capturing.setText("CONSUME");
                    break;
                    case Common.CaptureThreadState.PAUSE:PacketCaptureServiceProxy.setPacketCaptureThreadState(currentChosenInterface
                    ,CONSUME);
                    home_btn_start_capturing.setText("PAUSE");
                    break;
                    default:System.out.println("null state" + currentChosenInterface + PacketCaptureServiceProxy.getThreadState(currentChosenInterface));
                }
            }
        });

        //停止接口线程服务
        home_btn_stop_thread.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                PacketCaptureServiceProxy.setPacketCaptureThreadState(currentChosenInterface,Common.CaptureThreadState.QUIT);
                PacketCaptureServiceProxy.setPacketCaptureThreadState(currentChosenInterface,WAITING);
                hasOpenInterfaceName.remove(currentChosenInterface);
                home_btn_start_capturing.setText("START");
            }
        });
        //初始化choice box
        home_choice_box_receive_interface.setItems(nameList);
        //设置第一个接口为默认接口
        home_choice_box_receive_interface.setValue(nameList.get(0));
        //切换表格数据源
        home_choice_box_receive_interface.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                home_table_packet.setItems(packet.common.Common.getTableDataByInterfaceName(observable.getValue()).getPacketModelObservableList());
            }
        });
        //图像显示sysInfo
        home_img_notification.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DialogUtils.showSysInfoDialog(App.stage,event.getScreenX(),event.getScreenY(), packet.common.Common.getSysInfo());
            }
        });

        //清除textArea
        home_btn_clear_textarea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                home_textarea_logging.clear();
                home_textarea_logging.setText(new Date().toString() + " clear all infos");
            }
        });
        //



        home_btn_flash.setOnMouseClicked(event -> {
            home_table_packet.refresh();
            ObserverCenter.notifyLogging("manual refresh");
        });

        repository.setAutoFlashPeriodTime(1000,3000,new packet.repository.Callback<Object>() {
            @Override
            public void callback(Object o) {
                home_table_packet.refresh();
            }
        });

        home_ckbox_auto_flash.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (observable.getValue()){//如果选中
                    repository.setAutoFlashPeriodTime(1000, 3000, new packet.repository.Callback<Object>() {
                        @Override
                        public void callback(Object o) {
                            home_table_packet.refresh();
                        }
                    });
                    ObserverCenter.notifyLogging("auto refreshing..." + 3 + " seconds");
                }else{                      //如果没选中
                    repository.pauseAutoFlash();
                }
            }
        });

        home_textarea_logging.setEditable(false);

        setupPacketTableView();

        //为丢包事件注册监听器，发生丢包时回调该接口
        ObserverCenter.register(PACKET_LOSE_EVENT, s -> Platform.runLater(() -> {
            home_btn_lose_rate.setText(s);//主线程更新UI
        }));

        //table 右键事件注册
        GlobalMenu.getInstance().init(Arrays.asList("details","send","dump")).addListener((menuId, t) -> {
            PacketModel packetModel = (PacketModel)t;
            switch (menuId){
                case "details":
                    System.out.println("show Details");
                    break;
                case "send":
                   AlertUtils.showSenderWin(packetModel,nameList,new PcapPacket(1));
                    System.out.println("send");
                    break;
                case "dump":
                    System.out.println("dump");
                    break;
            }
        });
        //
        ObserverCenter.notifyLogging("初始化成功");
    }

    @Override
    public void destroy() {

    }

    @Override
    public HomeRepository initRepository() {
        return new HomeRepository();
    }

    /**
     * 用户初始化packetTable
     */
    private void setupPacketTableView() {
        home_table_packet.setRowFactory(new Callback<TableView<PacketModel>, TableRow<PacketModel>>() {
            @Override
            public TableRow<PacketModel> call(TableView<PacketModel> param) {
                TableRow<PacketModel> row = new TableRow<>();
                row.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println(event.getSource());
                        if (event.getClickCount()==2){
                            currentPcapPacket = null;
                            currentPcapPacket = new PcapPacket(row.getItem().getPcapPacket());
                            home_textarea_packet_overview.setText(currentPcapPacket.getState().toDebugString());
                            //
                        }else if(event.getClickCount()==1&&event.getButton()==MouseButton.SECONDARY){
                            GlobalMenu.getInstance().setObject(row.getItem()).show(home_table_packet,event.getScreenX(),event.getScreenY() );

                        }else{
                            GlobalMenu.getInstance().hide();
                        }
                    }
                });
                return row;
            }
        });

        home_table_packet.setOnScrollStarted(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                GlobalMenu.getInstance().hide();
            }
        });
//        setupCellValueFactory(packetIDColumn, PacketModel::idProperty);
//        setupCellValueFactory(packetTimeColumn,PacketModel::packetTimeProperty);
//        setupCellValueFactory(packetLengthColumn, PacketModel::packetLengthProperty);
//        setupCellValueFactory(packetProtocolColumn, PacketModel::packetProtocolProperty);
        packetIDColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PacketModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PacketModel, String> param) {
                return param.getValue().idProperty();
            }
        });
        packetTimeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PacketModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PacketModel, String> param) {
                return param.getValue().packetTimeProperty();
            }
        });
        packetLengthColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PacketModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PacketModel, String> param) {
                return param.getValue().packetLengthProperty();
            }
        });
        packetProtocolColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PacketModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PacketModel, String> param) {
                return param.getValue().packetProtocolProperty();
            }
        });
        packetInfoColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PacketModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PacketModel, String> param) {
                return param.getValue().packetInfoProperty();
            }
        });

        //默认选择第一个接口关联到table，和choice box的一致
        home_table_packet.setItems(packet.common.Common.getTableDataByInterfaceName(nameList.get(0)).getPacketModelObservableList());
    }

    /**
     * 属性绑定方法
     */
    private <T> void setupCellValueFactory(JFXTreeTableColumn<PacketModel, T> column, Function<PacketModel, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<PacketModel, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private void startCapture(String interfaceName){
        PacketCaptureServiceProxy.startCapturingPacket(interfaceName);
    }

    private void bindDataForInterface(String interfaceName){

    }
}
