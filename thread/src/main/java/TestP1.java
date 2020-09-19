public class TestP1 {
    public static void main(final String[] args) throws InterruptedException {
        final P1 v = new P1();
        // 线程 1：设置 b = 0
        final Thread t1 = new Thread(() -> {
            while (true) {
                v.set1();
            }
        });
        t1.start();

        // 线程 2：设置 b = -1
        final Thread t2 = new Thread(() -> {
            while (true) {
                v.set2();
            }
        });
        t2.start();

        // 线程 3：检查 0 != b && -1 != b
        final Thread t3 = new Thread(() -> {
            while (true) {
                v.check();
            }
        });
        //TimeUnit.SECONDS.sleep(5);
        t3.start();
    }

}
