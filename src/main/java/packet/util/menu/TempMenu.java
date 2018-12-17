package packet.util.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.HashMap;
import java.util.List;

public class TempMenu extends ContextMenu implements EventHandler<ActionEvent> {

    private static HashMap<String,TempMenu> tempMenuHashMap = new HashMap<>(3);
    private MenuCallback callback;
    private Object obj;
    private boolean hasShow = false;

    private TempMenu(){

    }

    public static TempMenu getInstance(String menuName){
        TempMenu tempMenu = tempMenuHashMap.get(menuName);
        if (tempMenu==null){
            tempMenu = new TempMenu();
            tempMenuHashMap.put(menuName,tempMenu);
        }
        return tempMenu;
    }

    /**
     * 要先初始化 再addListener
     * @param options
     * @return
     */
    public TempMenu init(List<String> options){
        if (hasShow)
            return this;
        return addMenuItems(options);
    }

    public void reflash(List<String> options){
        getItems().removeAll(getItems());
        addMenuItems(options);
        hasShow = false;
    }

    private TempMenu addMenuItems(List<String> menuItems)
    {
        for (String itemName:menuItems){
            MenuItem menuItem = new MenuItem(itemName);
            menuItem.setId(itemName);
            getItems().add(menuItem);
        }
        return this;
    }

    private void setCallback(MenuCallback menuCallback){
        this.callback = menuCallback;
    }

    public TempMenu addListener(MenuCallback callback){
        if (hasShow)
            return this;
        for (MenuItem menuItem:getItems()){
            menuItem.setOnAction(this);
        }
        setCallback(callback);
        return this;
    }

    public TempMenu setObject(Object obj){
        this.obj = obj;
        return this;
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println("hehehehehehhehe");
        MenuItem menuItem = (MenuItem) event.getSource();
        callback.callback(menuItem.getId(),obj);
    }

    public void show(Node node,double x,double y){
        hasShow = true;
        super.show(node,x,y);
    }
}
