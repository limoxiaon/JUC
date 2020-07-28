package day04;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
    解决线程安全的三种方式

    synchronized关键字
    1、同步代码块

    2、同步方法

    jdk1.5之后
    3、同步锁Lock提供了更加灵活的方式。
 */
public class LockDemo {
    public static void main(String[] args) {
        ThreadDemo_2 td=new ThreadDemo_2();

        new Thread(td,"一号窗口").start();
        new Thread(td,"二号窗口").start();
        new Thread(td,"三号窗口").start();
    }
}

class ThreadDemo_2 implements Runnable{

    private int ticket=10000;
    private Lock lock=new ReentrantLock();

    @Override
    public void run() {
        while(true) {
            lock.lock();
            try {
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + "完成售票" + --ticket);
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
