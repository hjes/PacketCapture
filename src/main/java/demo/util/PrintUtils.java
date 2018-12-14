package demo.util;

public class PrintUtils {

    private static class SINGLE_HOLDER{
        public static PrintUtils printUtils = new PrintUtils();
    }

    private void printMsg(String msg){
        System.out.println(msg);
    }

    private static PrintUtils getInstance(){
        return SINGLE_HOLDER.printUtils;
    }

    public static void print(String msg){
        getInstance().printMsg(msg);
    }
}
