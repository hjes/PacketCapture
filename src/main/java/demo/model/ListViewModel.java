package demo.model;

import java.util.LinkedList;
import java.util.List;

public class ListViewModel<T> {
    private LinkedList<T> linkedList;
    private int capacity;
    private int currentSize;
    private int returnType;

    public ListViewModel(int size){
        linkedList = new LinkedList<>();
        if (size>0){
            capacity = size;
        }else{
            capacity = 2048;
        }
    }
    public synchronized void add(T t){
        if (currentSize>=capacity){
            overSize(t);
        }else{
            linkedList.addFirst(t);
        }
        currentSize++;
    }

    private void overSize(T t){
        linkedList.removeLast();
        linkedList.addFirst(t);
    }

    public List<T> getList(){
        return linkedList;
    }

}
