/**
 * 一、Volatile 关键字
 * 1. 解决线程中：不加锁由于 内存可见性原因 导致出现异常，加上 锁 解决了异常，却导致执行效率极低！
 * 2.当多个线程进行操作共享数据时，可以保证内存中的数据可见。
 *          相较于 synchronized 是一种较为轻量级的同步策略。
 * 注意：
 * 1. volatile 不具备“互斥性”
 * 2. volatile 不能保证变量的“原子性”
 */
public class TestVolatile {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();

        /**
         * 内存可见性：当 while(true) 循环查询时就无法获取到 主存中已经修改过的 flag,故一直无法打印---
         */
        while(true){
            if(td.isFlag()){
                System.out.println("------------------");
                break;
            }
        }

    }

}

class ThreadDemo implements Runnable {

    private volatile boolean flag = false;

    @Override
    public void run() {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }

        flag = true;

        System.out.println("flag=" + isFlag());

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}