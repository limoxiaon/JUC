package day01;

public class VolatileDemo {

    public static void main(String[] args) {
        ThreadDemo threadDemo=new ThreadDemo();
        new Thread(threadDemo).start();
        while(true){
                if(threadDemo.isFlag()){
                System.out.println("--------------------------");
                break;
                }
        }
    }
}

class ThreadDemo implements Runnable{

    private volatile boolean flag=false;

    @Override
    public void run() {
        flag=true;
        System.out.println("flag="+isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
