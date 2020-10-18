import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TheadPoolTest {
    public static void main(String[] args) {

//        ExecutorService service = Executors.newFixedThreadPool(1);
//        Runnable r = () -> System.out.println(1 / 0);
//        service.submit(r);
//        service.shutdown();

        ExecutorService executorService = Executors.newCachedThreadPool();
        Thread thread = new Thread(() -> {
            int i=1/0;
            System.out.println("test");
        });
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int i=1/0;
                System.out.println("run0");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("run1");
            }
        };


        //executorService.execute(thread);
        //executorService.execute(runnable);

        executorService.submit(thread);
        Future<?> future = executorService.submit(runnable);
        System.out.println("future.get() start");
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("exception");
        }
        System.out.println("future.get() end");

        executorService.shutdown();
    }
}
