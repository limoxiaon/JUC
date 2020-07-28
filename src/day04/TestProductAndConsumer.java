package day04;
/*
    生产者和消费者模型
    为了避免产生虚假唤醒，wait方法需要用while嵌套进去。
 */
public class TestProductAndConsumer {

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

    //进货
    public synchronized void get(){
        while(product>=1){//为了避免虚假唤醒
            System.out.println(Thread.currentThread().getName()+"货已满");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+":"+ ++product);
        this.notifyAll();

    }

    //出货
    public synchronized void sale(){
        while(product<=0){
            System.out.println(Thread.currentThread().getName()+"缺货");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+":"+ --product);
        this.notifyAll();
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
