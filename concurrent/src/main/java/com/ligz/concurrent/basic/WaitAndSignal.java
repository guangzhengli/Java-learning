package com.ligz.concurrent.basic;

public class WaitAndSignal {
    private boolean flag;
    int i = 0;

    public static void main(String[] args) {

        WaitAndSignal waitAndSignal = new WaitAndSignal();
        Thread thread1 = new Thread(() -> {
            synchronized (waitAndSignal) {
                try {
                    while (waitAndSignal.i < 100) {
                        if (waitAndSignal.flag) {
                            waitAndSignal.i++;
                            waitAndSignal.flag = false;
                            System.out.println(waitAndSignal.i);
                            waitAndSignal.notify();
                        } else {
                            waitAndSignal.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (waitAndSignal) {
                try {
                    while (waitAndSignal.i < 100) {
                        if (!waitAndSignal.flag) {
                            waitAndSignal.i++;
                            waitAndSignal.flag = true;
                            System.out.println(waitAndSignal.i);
                            waitAndSignal.notify();
                        } else {
                            waitAndSignal.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        thread2.start();
    }
}
