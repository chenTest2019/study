package com.chen.interview.boss.one.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *
 *将getFast中的3个方法改为多线程 谁快首先返回谁 中断其他线程
 *
 * @author jack
 */
public class TestThread {
    private static  List result;
    private static final Object obj=new Object();
    private Thread a = null;
    private Thread b = null;
    private Thread c = null;
    public static void main(String[] args) {
        TestThread test = new TestThread();
        CountDownLatch countDownLatch=new CountDownLatch(1);


        test.a=new Thread(()->{
            List tmp=test.a();
            synchronized (obj){
                result=tmp;
                test.b.interrupt();
                test.c.interrupt();
            }

            countDownLatch.countDown();
        });
        test.b=new Thread(()->{
            List tmp=test.b();
            synchronized (obj){
                result=tmp;
                test.a.interrupt();
                test.c.interrupt();
            }

            countDownLatch.countDown();
        });
        test.c=new Thread(()->{
            List tmp=test.c();
            synchronized (obj){
                result=tmp;
                test.a.interrupt();
                test.b.interrupt();
            }

            countDownLatch.countDown();

        });
        test.a.start();
        test.b.start();
        test.c.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(result);

    }


    List getFast(){

        List result;

        result=a();

        result=b();

        result=c();

        return result;
    }

    List a(){
        List<String> list=null;
        try {
            TimeUnit.SECONDS.sleep(7);
            list=new ArrayList<>();
            list.add("a");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }
    List b(){
        List<String> list = null;
        try {
            TimeUnit.SECONDS.sleep(1);
            list=new ArrayList<>();
            list.add("b");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return list;
    }
    List c(){
        List<String> list = null;
        try {
            TimeUnit.SECONDS.sleep(3);
            list=new ArrayList<>();
            list.add("c");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return list;
    }
}
