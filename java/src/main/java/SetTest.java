import java.util.*;

public class SetTest {
    public static void main(String[] args) {

        HashMap<String,String> hashMap = new HashMap<>();

        hashMap.put("a","1");
        hashMap.put("b","2");


        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("a","1");
        treeMap.put("b","2");

        System.out.println(hashMap);
        System.out.println(treeMap);

        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("a");
        hashSet.add("b");

        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("a");
        treeSet.add("b");



        Hashtable<String,String> hashtable=new Hashtable<>();



    }
}
