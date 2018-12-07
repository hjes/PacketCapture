package common;

import exceptions.NotRegisterException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一用Class.getName注册
 */
public class ObserverCenter {
    public static Map<String,ArrayList<Observer<String>>> observerMap = new HashMap<>(5);
    public static void register(String name,Observer<String> observer){
        if (observerMap.get(name) == null){
            ArrayList<Observer<String>> observers = new ArrayList<>(5);
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
}
