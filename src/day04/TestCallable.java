package day04;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*
    1、创建线程的第三种方式:实现Callable接口，可以有返回值。
    2、Callable的实现需要FutureTask这个实现类的支持。
 */
public class TestCallable {
    public static void main(String[] args) {
        ThreadDemo td=new ThreadDemo();
        FutureTask<Integer> result=new FutureTask<>(td);

        new Thread(result).start();

        try {
            Integer sum=result.get();  //FutureTask可以当做闭锁来使用。
            System.out.println(sum);
            System.out.println("--------------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class ThreadDemo implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        Integer sum=0;
        for (int i = 0; i < 1000000000; i++) {
            sum+=i;
        }
        return sum;
    }
}
