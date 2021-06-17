package concurrent.basic;

public class JoinThread {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println(1);
        });
        thread.start();
        thread.join();
        System.out.println(2);
    }
}
