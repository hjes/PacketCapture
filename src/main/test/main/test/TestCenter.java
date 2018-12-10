package main.test;

import demo.model.ListViewModel;
import demo.model.PacketModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;
import packet.PackageCapture;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public class TestCenter {
    @Test
    public void listModelTest(){
        ListViewModel<Integer> listViewModel = new ListViewModel<>(10);
        for (int i = 0;i<15;i++)
            listViewModel.add(i);
        for (int i:listViewModel.getList()){
            System.out.println(i);
        }
    }

    @Test
    public void captureTest(){
        PackageCapture.mainCapture(new String[]{"1"});
    }

    @Test
    public void linkedQueueTest(){
        LinkedBlockingQueue<String> stringLinkedBlockingQueue = new LinkedBlockingQueue<>();
        stringLinkedBlockingQueue.add("1");
        stringLinkedBlockingQueue.add("2");
        stringLinkedBlockingQueue.add("3");
        stringLinkedBlockingQueue.add("4");
        System.out.println(stringLinkedBlockingQueue.size());
        try {
            stringLinkedBlockingQueue.take();
            stringLinkedBlockingQueue.take();
            stringLinkedBlockingQueue.take();
            System.out.println(stringLinkedBlockingQueue.size());
            stringLinkedBlockingQueue.take();
            System.out.println(stringLinkedBlockingQueue.size());
            stringLinkedBlockingQueue.take();
            System.out.println(stringLinkedBlockingQueue.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void libraryPathTest(){
        System.getProperty("java.library.path");
    }

    @Test
    public void listTest(){
        ListViewModel<String> listViewModel = new ListViewModel<>(5);
        ObservableList<String> dummyData = FXCollections.observableList(listViewModel.getList());
        for (int i = 0;i<10;i++)
            listViewModel.add(String.valueOf(i));
        for (String s:dummyData)
            System.out.println(s);
    }

    private static int i = 0;
    private Timer timer;

    @Test
    public void timerTest(){
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                i++;
                System.out.println(i + timer.purge());
                if (i==5)
                    timer.cancel();
            }
        },1000,3000);
    }
}
