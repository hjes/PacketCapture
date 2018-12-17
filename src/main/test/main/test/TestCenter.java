package main.test;

import common.ThreadObserver;
import packet.model.ListViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;

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

    @Test
    public void threadPauseTest(){
        final ThreadObserver threadObserver = new ThreadObserver();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("sleeping1");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("notify it1");
                threadObserver.notifyNow();
                System.out.println("sleeping2");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("notify it2");
                threadObserver.notifyNow();
                System.out.println("notify it end");
            }
        }).start();
        System.out.println("wait1");
        threadObserver.waitNow();
        System.out.println("notify1");
        System.out.println("wait2");
        threadObserver.waitNow();
        System.out.println("notify 1 1 1 1 ");
    }

    @Test
    public void staticTest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                UITest.testString("heiheihei");
            }
        }).start();
        UITest.testString("enenen");
    }

    @Test
    public void synchronizedTest() throws InterruptedException {
        UITest UI = new UITest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (UI){
                    System.out.println("ui sleep");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("unlock UI");
                }
            }
        }).start();
        Thread.sleep(1000);
        synchronized (UI){
            System.out.println(UI);
        }
        System.out.println("out ui");
    }
}
