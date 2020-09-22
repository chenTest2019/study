package com.chen.interview.boss.one.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @author jack
 */
public class MyPark {
    public static void main(String[] args) {

        Thread c= new Thread(()->{
            LockSupport.park();
            System.out.println("业务逻辑C");
        });
        Thread b= new Thread(()->{
            LockSupport.park();
            System.out.println("业务逻辑B");
            LockSupport.unpark(c);
        });
        Thread a= new Thread(()->{
            System.out.println("我要先执行A");
            LockSupport.unpark(b);
        });
        a.start();
        b.start();
        c.start();
    }
}
