package interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {
    public static void main(String[] args) {


        List<Integer> l1 = new ArrayList<>();
        List<Character> l2 = new ArrayList<>();
        l1=Collections.synchronizedList(l1);
        l2=Collections.synchronizedList(l2);
        for (int i = 1; i <= 26; i++) {
            l1.add(i);
            l2.add((char) (97 + (i-1)));
        }

        for (int i = 0; i < 1000; i++) {
            cctv(l1,l2);
            //System.out.println("");
        }

    }

    public static void cctv(List<Integer> l1, List<Character> l2){
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition consumer = reentrantLock.newCondition();
        Condition producer = reentrantLock.newCondition();
        new Thread(()->{
            String name=Thread.currentThread().getName();
            reentrantLock.lock();

            try {for (Integer integer : l1) {

                consumer.await();
                if(name.contains("Thread-1")){
                    System.out.print(name+":"+integer+"\t");
                }else{
                    //System.out.print(integer);
                }

                producer.signal();

            }} catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                producer.signalAll();
                reentrantLock.unlock();
            }

        }).start();
        new Thread(()->{
            String name=Thread.currentThread().getName();
            reentrantLock.lock();
            try {for (Character character : l2) {
                if(name.contains("Thread-1")){
                    System.out.print(name+":"+character+"\t");
                }else {
                    //System.out.print(character);
                }

                consumer.signal();

                producer.await();
            }
            }catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                consumer.signalAll();
                reentrantLock.unlock();
            }
        }).start();
    }


}
