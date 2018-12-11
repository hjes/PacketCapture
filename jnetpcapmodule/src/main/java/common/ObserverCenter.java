package common;

import exceptions.NotRegisterException;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一用Class.getName注册
 */
public class ObserverCenter {
    public static Map<String,ArrayList<Observer<String>>> observerMap = new HashMap<>(5);
    public static void register(String name,Observer<String> observer){
        if (observerMap.get(name) == null){
            ArrayList<Observer<String>> observers = new ArrayList<>(1);
            observers.add(observer);
            observerMap.put(name,observers);
        }else{
            observerMap.get(name).add(observer);
        }
    }

    public static void unregister(String name){
        observerMap.remove(name);
    }

    public static void unregister(String name,Observer<String> observer){
        ArrayList<Observer<String>> observers = observerMap.get(name);
        observers.remove(observer);
    }

    public static void notifyObservers(String name,String message){
        ArrayList<Observer<String>> observers = observerMap.get(name);
        if (observers==null||observers.size()==0)
            return;
        for (Observer<String> observer:observers){
            observer.update(message);
        }
    }

    public static void notifyLogging(String message,boolean withDate){
        if (withDate)
            observerMap.get("logging").get(0).update(new Date().toString() + message);
        else
            observerMap.get("logging").get(0).update(message);
    }

    public static void notifyLogging(String message){
        if (observerMap.get("logging")!=null)
            notifyLogging(message,true);
    }
}
