package concurrent.basic;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitAndSignal {
    int i = 0;

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        AwaitAndSignal awaitAndSignal = new AwaitAndSignal();
        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {
                while (awaitAndSignal.i < 100) {
                    if (awaitAndSignal.i % 2 == 0) {
                        awaitAndSignal.i++;
                        System.out.println(awaitAndSignal.i);
                        condition.signalAll();
                    } else {
                        condition.await();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            lock.lock();
            try {
                while (awaitAndSignal.i < 100) {
                    if (awaitAndSignal.i % 2 != 0) {
                        awaitAndSignal.i++;
                        System.out.println(awaitAndSignal.i);
                        condition.signalAll();
                    } else {
                        condition.await();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        thread1.start();
        thread2.start();
    }
}
