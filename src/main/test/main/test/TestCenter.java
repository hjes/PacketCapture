package main.test;

import demo.model.ListViewModel;
import org.junit.Test;
import packet.PackageCapture;

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
}
