package com.ligz.concurrent.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProductAndConsumer {
    private Lock lock;
    private Condition empty;
    private Condition full;
    private List<Integer> list;

    public ProductAndConsumer(Lock lock, Condition empty, Condition full, List<Integer> list) {
        this.lock = lock;
        this.empty = empty;
        this.full = full;
        this.list = list;
    }

    public void put(Integer i) {
        lock.lock();
        try {
            while (list.size() > 3) {
                full.await();
            }
            list.add(i);
            System.out.println("put: " + i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            empty.signalAll();
            lock.unlock();
        }
    }

    public Integer get() {
        lock.lock();
        try {
            while (list.isEmpty()) {
                empty.await();
            }
            Integer i = list.remove(0);
            System.out.println("get: " + i);
            return i;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            full.signalAll();
            lock.unlock();
        }
        return -1;
    }


    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        ProductAndConsumer productAndConsumer = new ProductAndConsumer(lock, lock.newCondition(), lock.newCondition(), new ArrayList<>());
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                productAndConsumer.put(i);
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                productAndConsumer.get();
            }
        });
        thread1.start();
        thread2.start();
    }
}
