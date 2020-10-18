import java.util.ArrayList;
import java.util.LinkedList;

public class ListTest {
    public static void main(String[] args) {
        LinkedList<String> linkedList=new LinkedList<>();
        ArrayList<String> arrayList=new ArrayList<>();
        long l = System.nanoTime();
        int count=10_0000;
        //int count=10;
        //int count=100;
        for (int i = 0; i < count; i++) {
            linkedList.add(0,"0");
        }
        System.out.println("耗时："+(System.nanoTime()-l)/1000);
        l = System.nanoTime();
        for (int j = 0; j < count; j++) {
            arrayList.add(0,"0");
        }
        System.out.println("耗时："+(System.nanoTime()-l)/1000);
    }

}
