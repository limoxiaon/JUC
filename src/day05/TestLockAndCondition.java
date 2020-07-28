package day05;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
   1、Condition控制线程通信
 */

public class TestLockAndCondition {

    public static void main(String[] args) {
        Clerk clerk=new Clerk();

        Product product=new Product(clerk);
        Consumer consumer=new Consumer(clerk);

        new Thread(product,"生产者A").start();
        new Thread(consumer,"消费者B").start();

        new Thread(product,"生产者C").start();
        new Thread(consumer,"消费者D").start();
    }
}

class Clerk{
    private int product=0;
    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();

    //进货
    public void get(){
        lock.lock();
        try{
            while(product>=1){//为了避免虚假唤醒
                System.out.println(Thread.currentThread().getName()+"货已满");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+":"+ ++product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    //出货
    public void sale(){
        lock.lock();
        try{
            while(product<=0){
                System.out.println(Thread.currentThread().getName()+"缺货");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+":"+ --product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }
}

class Product implements Runnable{
    private Clerk clerk;

    public Product(Clerk clerk){
        this.clerk=clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.get();
        }
    }
}

class Consumer implements Runnable{
    private Clerk clerk;

    public Consumer(Clerk clerk){
        this.clerk=clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}

