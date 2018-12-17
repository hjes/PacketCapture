package packet.util.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.List;

/**
 */
public class GlobalMenu extends ContextMenu implements EventHandler<ActionEvent> {
    /**
     * 单例
     */
    private static GlobalMenu INSTANCE = null;

    private MenuCallback callback;

    private Object obj;

    private GlobalMenu(){

    }
    /**
     * 获取实例
     * @return GlobalMenu
     */
    public static GlobalMenu getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new GlobalMenu();
        }
        return INSTANCE;
    }

    /**
     * 初始化
     * @param menuItems
     * @return
     */
    public GlobalMenu init(List<String> menuItems){
        return getInstance().addMenuItems(menuItems);
    }

    /**
     * 私有构造函数
     */
    private GlobalMenu addMenuItems(List<String> menuItems)
    {

        for (String itemName:menuItems){
            MenuItem menuItem = new MenuItem(itemName);
            menuItem.setId(itemName);
            getItems().add(menuItem);
        }
        return INSTANCE;
    }

    public GlobalMenu setObject(Object obj){
        this.obj = obj;
        return INSTANCE;
    }

    /**
     * 添加回调接口，返回点击Item的ID
     * @param callback 回调
     */
    public void addListener(MenuCallback callback){
        for (MenuItem menuItem:getInstance().getItems()){
            menuItem.setOnAction(this);
        }
        setCallback(callback);
    }

    private void setCallback(MenuCallback callback){
        this.callback = callback;
    }

    @Override
    public void handle(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        callback.callback(menuItem.getId(),obj);
    }
}

