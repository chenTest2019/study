package com.chen.zookeeper.lock;

import org.apache.zookeeper.KeeperException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class LockTest {

    CountDownLatch countDownLatch = null;


    @BeforeEach
    void setUp() throws IOException {
        countDownLatch = new CountDownLatch(50);
    }

    @AfterEach
    void tearDown() {

    }

    /**
     * 模拟多个客户端测试 只用于分布式环境 每个线程代表一个链接 每个链接代表一个客户端
     * 如果测试单机 要生成一个Lock对象 所有线程共享
     *
     * @throws IOException
     */
    @Test
    public void testLock() throws IOException, InterruptedException {

        String name = Thread.currentThread().getName();
        System.out.println("test:" + name);

        long count = countDownLatch.getCount();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < count - 1; i++) {
            Thread thread = new Thread(() -> {
                Lock lock = new Lock();
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
                try {
                    countDownLatch.await();
                    lock.getLock();
                    System.out.println(Thread.currentThread().getName() + "抢到锁");
                    Thread.sleep(1000);
                    lock.releaseLock();
                    System.out.println(Thread.currentThread().getName() + "已释放锁");
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

        //System.in.read();
    }

    /**
     * 模拟单机测试 要生成一个Lock对象 所有线程共享
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void testLockSingle() throws IOException, InterruptedException {

        String name = Thread.currentThread().getName();
        System.out.println("test:" + name);
        Lock lock = new Lock();
        long count = countDownLatch.getCount();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < count - 1; i++) {
            Thread thread = new Thread(() -> {

                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
                try {
                    countDownLatch.await();
                    lock.getLock();
                    System.out.println(Thread.currentThread().getName() + "抢到锁");
                    TimeUnit.SECONDS.sleep(5);
                    lock.releaseLock();
                    System.out.println(Thread.currentThread().getName() + "已释放锁");
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

        //System.in.read();
    }
}