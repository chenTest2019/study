package com.chen.zookeeper.lock;

import lombok.SneakyThrows;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

public class Lock {


    private ZooKeeper zk = null;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    private NodeWatch nodeWatch = new NodeWatch();
    private SessionWatch sessionWatch = new SessionWatch();

    private final String lock = "/lock";
    private volatile CallBack current;

    public void getLock() throws KeeperException, InterruptedException, BrokenBarrierException {
        getLock(lock);
    }

    private synchronized void getLock(String path) {
        Thread thread = Thread.currentThread();
        String name = thread.getName();
        zk.create(path, name.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new CallBack(zk, Thread.currentThread()), this);
        LockSupport.park();
    }

    public void releaseLock() throws KeeperException, InterruptedException {
        Thread thread = Thread.currentThread();
        //System.out.println("thread:"+thread.getName()+"开始");
        zk.delete(this.current.name, this.current.stat.getVersion());
        //System.out.println("thread:"+thread.getName()+"已删除");

    }

    public Lock() {
        try {
            zk = ZkUtils.getZk(sessionWatch);
            countDownLatch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        nodeWatch.me = Thread.currentThread();
        try {
            zk.addWatch(lock, nodeWatch, AddWatchMode.PERSISTENT);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    static class SessionWatch implements Watcher {
        private ZooKeeper zk;

        public SessionWatch() {
        }

        public SessionWatch(ZooKeeper zk) {
            this.zk = zk;
        }

        @SneakyThrows
        @Override
        public void process(WatchedEvent event) {
            String name = Thread.currentThread().getName();

            String path = event.getPath();
            Event.KeeperState state = event.getState();
            Event.EventType type = event.getType();
            //System.out.println(event);
            /*switch (type) {
                case None:
                    System.out.println("SessionWatch type:"+name);
                    break;
                case NodeCreated:
                    System.out.println("SessionWatch NodeCreated:"+name);
                    break;
                case NodeDeleted:
                    System.out.println("SessionWatch NodeDeleted:"+name);
                    break;
                case NodeDataChanged:
                    System.out.println("SessionWatch NodeDataChanged:"+name);
                    break;
                case NodeChildrenChanged:
                    System.out.println("SessionWatch NodeChildrenChanged:"+name);
                    break;
                case DataWatchRemoved:
                    System.out.println("SessionWatch DataWatchRemoved:"+name);
                    break;
                case ChildWatchRemoved:
                    System.out.println("SessionWatch ChildWatchRemoved:"+name);
                    break;
                case PersistentWatchRemoved:
                    System.out.println("SessionWatch PersistentWatchRemoved:"+name);
                    break;
            }*/
            switch (state) {

                case Disconnected:
                    //System.out.println("SessionWatch Disconnected:"+name);
                    break;
                case SyncConnected:
                    System.out.println("SessionWatch SyncConnected:" + name);
                    countDownLatch.countDown();
                    break;
                case AuthFailed:
                    //System.out.println("SessionWatch AuthFailed:"+name);
                    break;
                case ConnectedReadOnly:
                    //System.out.println("SessionWatch ConnectedReadOnly:"+name);
                    break;
                case SaslAuthenticated:
                    //System.out.println("SessionWatch SaslAuthenticated:"+name);
                    break;
                case Expired:
                    //System.out.println("SessionWatch Expired:"+name);
                    break;
                case Closed:
                    //System.out.println("SessionWatch Closed:"+name);
                    break;
            }
        }
    }

    static class NodeWatch implements Watcher {
        private Thread me;
        private ZooKeeper zk;

        public NodeWatch() {
        }

        public NodeWatch(ZooKeeper zk) {
            this.zk = zk;
        }

        @SneakyThrows
        @Override
        public void process(WatchedEvent event) {
            String name = Thread.currentThread().getName();

            String path = event.getPath();
            Event.KeeperState state = event.getState();
            Event.EventType type = event.getType();
            //System.out.println(event);
            switch (type) {
                case None:
                    //System.out.println("NodeWatch type "+name);
                    break;
                case NodeCreated:
                    //System.out.println("NodeWatch NodeCreated "+name);
                    //System.out.println("me.getName():"+me.getName());
                    break;
                case NodeDeleted:
                    System.out.println("NodeWatch NodeDeleted " + name);
                    //System.out.println("me.getName():"+me.getName());
                    //can=true;
                    //cyclicBarrier.await();
                    break;
                case NodeDataChanged:
                    //System.out.println("NodeWatch NodeDataChanged "+name);
                    break;
                case NodeChildrenChanged:
                    //System.out.println("NodeWatch NodeChildrenChanged "+name);
                    break;
                case DataWatchRemoved:
                    //System.out.println("NodeWatch DataWatchRemoved "+name);
                    break;
                case ChildWatchRemoved:
                    //System.out.println("NodeWatch ChildWatchRemoved "+name);
                    break;
                case PersistentWatchRemoved:
                    //System.out.println("NodeWatch PersistentWatchRemoved "+name);
                    break;
            }
            /*switch (state) {

                case Disconnected:
                    System.out.println("NodeWatch Disconnected "+name);
                    break;
                case SyncConnected:
                    System.out.println("NodeWatch SyncConnected "+name);
                    break;
                case AuthFailed:
                    System.out.println("NodeWatch AuthFailed "+name);
                    break;
                case ConnectedReadOnly:
                    System.out.println("NodeWatch ConnectedReadOnly "+name);
                    break;
                case SaslAuthenticated:
                    System.out.println("NodeWatch SaslAuthenticated "+name);
                    break;
                case Expired:
                    System.out.println("NodeWatch Expired "+name);
                    break;
                case Closed:
                    System.out.println("NodeWatch Closed "+name);
                    break;
            }*/

        }
    }

    static class CallBack implements AsyncCallback.StringCallback, AsyncCallback.VoidCallback, Watcher {

        private final ZooKeeper zk;
        private final Thread thread;
        private Lock lock;
        private String name;
        private final Stat stat;

        public CallBack(ZooKeeper zk, Thread thread) {
            this.zk = zk;
            this.thread = thread;
            this.stat = new Stat();
        }

        //AsyncCallback.StringCallback
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            try {
                if(name==null||"".equals(name)){
                    System.out.println("创建节点失败");
                    //todo
                }
                this.name = name;
                this.lock = (Lock) ctx;
                zk.getData(name, false, this.stat);
                List<String> children = zk.getChildren("/", false);
                Collections.sort(children);
                int i = children.indexOf(name.replaceAll("/", ""));
                if (i == 0) {
                    LockSupport.unpark(thread);
                    this.lock.current = this;
                } else {
                    zk.addWatch("/" + children.get(i - 1), this, AddWatchMode.PERSISTENT);
                }
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
            //todo
        }

        //AsyncCallback.VoidCallback
        @Override
        public void processResult(int rc, String path, Object ctx) {
            //唤醒线程
            System.out.println("thread" + thread + "唤醒aaaa");
            LockSupport.unpark(this.thread);
        }

        //Watcher
        @Override
        public void process(WatchedEvent event) {
            //唤醒线程
            //System.out.println(thread+"被上一个节点唤醒");
            //this.name=event.getPath();
            this.lock.current = this;
            LockSupport.unpark(this.thread);
        }
    }
}
