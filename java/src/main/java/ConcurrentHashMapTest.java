import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
    public static void main(String[] args) {
        ConcurrentHashMapTest concurrentHashMapTest = new ConcurrentHashMapTest();
        concurrentHashMapTest.b();
        System.out.println("OK");
    }

    void a(){
        ConcurrentHashMap<String,String> concurrentHashMap=new ConcurrentHashMap<>();
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("a","f");

        long start=System.nanoTime();
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 100; j++) {
                    concurrentHashMap.put(Thread.currentThread().getName()+"-"+j,""+j);
                }
            },"name"+i).start();
        }

        System.out.println(System.nanoTime()-start);
    }

    void test(){
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(16);
        map.computeIfAbsent(
                "AaAa",
                key ->  map.computeIfAbsent("BBBB", key2 -> 42)
        );
    }

    void b(){
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        System.out.println("start.");
        map.computeIfAbsent("t",
                (String t) -> {
                    map.put("t", "t");
                    return "t";
                });
        System.out.println("fin.");
    }
}
