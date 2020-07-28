package day04;

import java.util.concurrent.CountDownLatch;

/*
CountDownLatch:闭锁：当完成某些操作时，只有其他的线程全部操作完毕后，当前的线程才会继续。
 */
public class LatchDemo {

    public static void main(String[] args) {
        CountDownLatch latch=new CountDownLatch(5);
        Test test=new Test(latch);

        long start=System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            new Thread(test).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end=System.currentTimeMillis();
        System.out.println("总消耗时间为:"+(end-start));
    }
}

class Test implements Runnable{

    private CountDownLatch latch;

    public Test(CountDownLatch latch){
        this.latch=latch;
    }

    @Override
    public void run() {
        synchronized (this){
            try{
                for (int i = 0; i < 50000; i++) {
                    if(i%2==0) {
                        System.out.println(i);
                    }
                }
            }finally {
                latch.countDown();
            }
        }
    }
}
