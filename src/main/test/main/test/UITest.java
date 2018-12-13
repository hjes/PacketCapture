package main.test;

import javafx.scene.control.Dialog;
import org.junit.Test;


public class UITest {
    public static void testString(String test){
        StringBuffer string1 = new StringBuffer("hello");
        string1.append(test);
        int i = 0;
        while(true){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            string1.append(String.valueOf(i));
            System.out.println(i + string1.toString());
        }
    }
}
