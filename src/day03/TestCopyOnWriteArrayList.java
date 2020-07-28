package day03;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
/*
    CopyOnWriteArrayList:"写入并复制"
    读操作时，没有使用锁
    写操作时，才对添加操作上锁了
    适合在读操作多写入少的时候使用
    注意：添加操作多时，效率低，因为每次添加时都会进行复制，开销非常大。
 */
public class TestCopyOnWriteArrayList {
    public static void main(String[] args) {
        HelloThread helloThread=new HelloThread();
        for (int i = 0; i <10; i++) {
            new Thread(helloThread).start();
        }
    }
}

class HelloThread implements  Runnable{
    //边迭代边添加会出现并发修改异常
    //private static List<String> list= Collections.synchronizedList(new ArrayList<>());

    private static CopyOnWriteArrayList<String> list=new CopyOnWriteArrayList<>();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        Iterator<String> iterator=list.iterator();
        //这里会结束，则说明了读写操作是分离的，在迭代器遍历的时候，数组还没有被新的副本数组进行修改。
        while(iterator.hasNext()){
            System.out.println(Thread.currentThread().getName()+"   "+iterator.next());
            list.add("AA");
        }
    }
}
