package com.chen.zookeeper.lock;

import org.apache.zookeeper.KeeperException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;

public class LockGuide {

    public static void main(String[] args) throws InterruptedException {

        //模拟多个客户端测试 分布式环境 每个线程代表一个链接 每个链接代表一个客户端

        CountDownLatch countDownLatch=new CountDownLatch(50);
        long count=countDownLatch.getCount();
        List<Thread> threadList=new ArrayList<>();
        for (int i = 0; i < count-1; i++) {
            Thread thread=new Thread(()->{
                Lock lock=new Lock();
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
                try {
                    countDownLatch.await();
                    lock.getLock();
                    System.out.println(Thread.currentThread().getName()+"抢到锁");
                    Thread.sleep(1000);
                    lock.releaseLock();
                    System.out.println(Thread.currentThread().getName()+"已释放锁");
                } catch (InterruptedException | KeeperException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            threadList.add(thread);
            thread.start();
        }
        countDownLatch.countDown();
        for (Thread thread : threadList) {
            thread.join();
        }
    }
}
