package main.test;

import demo.model.ListViewModel;
import org.junit.Test;
import packet.PackageCapture;

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
        PackageCapture.mainCapture("hello");
    }
}
