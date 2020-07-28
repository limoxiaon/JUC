package day02;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
    public static void main(String[] args) {
        Test td=new Test();
        for (int i = 0; i < 10; i++) {
            new Thread(td).start();
        }
    }
}

class Test implements  Runnable{

    private AtomicInteger i=new AtomicInteger();

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"   "+getI());
    }

    public int getI() {
        return i.getAndIncrement();
    }
}
