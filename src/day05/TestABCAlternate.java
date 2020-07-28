package day05;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
    让三个线程交替打印，打印20遍
    例如:ABCACBACB.....................
 */

public class TestABCAlternate {
    public static void main(String[] args) {
        Loop loop=new Loop();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 20; i++) {
                    loop.loopA(i);
                }
            }
        },"A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 20; i++) {
                    loop.loopB(i);
                }
            }
        },"B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 20; i++) {
                    loop.loopC(i);
                    System.out.println("------------------------");
                }
            }
        },"C").start();


    }
}

class Loop{
    private int number=1;//当前正在运行进程的标记
    private Lock lock=new ReentrantLock();
    private Condition condition1=lock.newCondition();
    private Condition condition2=lock.newCondition();
    private Condition condition3=lock.newCondition();

    public void loopA(int totalLoop){
        lock.lock();
        try{
            //1、判断
            if(number!=1){
                condition1.await();
            }

            //2、遍历
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
            }

            //3、通知下一个进程打印
            number=2;
            condition2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void loopB(int totalLoop){
        lock.lock();
        try{
            //1、判断
            if(number!=2){
                condition2.await();
            }

            //2、遍历
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
            }

            //3、通知下一个进程打印
            number=3;
            condition3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void loopC(int totalLoop){
        lock.lock();
        try{
            //1、判断
            if(number!=3){
                condition3.await();
            }

            //2、遍历
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
            }

            //3、通知下一个进程打印
            number=1;
            condition1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
