package basic;

import org.junit.Test;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.nio.ByteBuffer;

/**
 * 一、测试 缓冲区；在 Java 中负责数据的 存取。缓冲区就是数组，用于存储不同的数据类型的数据；
 * 根据数据类型不同，产生不同相应类型的缓冲区（Boolean 除外）
 * ByteBuffer;CharBuffer;ShortBuffer;FloatBuffer;IntBuffer;DoubleBuffer;
 * 上述缓冲区的管理方式几乎一致，都是同 allocate() 获取缓冲区；
 * 二、缓冲区存取数据的两个核心方法：
 * put()、get()
 * 四、缓冲区中四个核心属性
 * capacity：容量、一丹声明不能改变
 * limit：表示 limit 后的数据是不能进行读写的
 * position：表示 正在操作数据 的位置
 * mark:用于标记当前 position 的位置，可以通过 reset() 恢复到 mark 的位置；
 *
 * 0 <= mark <= positoin <= limit <= capacity
 *
 * 四、直接缓冲区与非直接缓冲区：
 * 非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
 * 直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
 */
public class TestBuffer {

    @Test
    public void test3() {
        //分配直接缓冲区
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);

        System.out.println(buf.isDirect()); //true
    }

    @Test
    public void test2() {
        String str = "abcde";

        ByteBuffer buf = ByteBuffer.allocate(1024);

        buf.put(str.getBytes());

        buf.flip();

        byte[] dst = new byte[buf.limit()]; //dst.length() = 5;
        buf.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2));  //ab
        System.out.println(buf.position()); //2

        //mark() : 标记
        buf.mark();

        buf.get(dst, 2, 2);
        System.out.println(new String(dst, 2, 2));  //cd
        System.out.println(buf.position()); //4

        //reset() : 恢复到 mark 的位置
        buf.reset();
        System.out.println(buf.position()); //2

        //判断缓冲区中是否还有剩余数据
        if (buf.hasRemaining()) {

            //获取缓冲区中可以操作的数量
            System.out.println(buf.remaining());    //3
        }
    }

    /**
     * 测试 不同方法下 缓冲区中 索引 位置、limit 的变化
     */
    @Test
    public void test1() {
        String str = "abcde";

        //1. 分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        System.out.println("-----------------allocate()----------------");
        System.out.println(buf.position()); //0
        System.out.println(buf.limit());    //1024
        System.out.println(buf.capacity()); //1024

        //2. 利用 put() 存入数据到缓冲区中
        buf.put(str.getBytes());

        System.out.println("-----------------put()----------------");
        System.out.println(buf.position()); //5
        System.out.println(buf.limit());    //1024
        System.out.println(buf.capacity()); //1024

        //3. 切换读取数据模式
        buf.flip();

        System.out.println("-----------------flip()----------------");
        System.out.println(buf.position()); //0
        System.out.println(buf.limit());    //1024
        System.out.println(buf.capacity()); //1024

        //4. 利用 get() 读取缓冲区中的数据
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst, 0, dst.length)); //abcde

        System.out.println("-----------------get()----------------");
        System.out.println(buf.position()); //5
        System.out.println(buf.limit());    //5
        System.out.println(buf.capacity()); //1024

        //5. rewind() : 可重复读
        buf.rewind();

        System.out.println("-----------------rewind()----------------");
        System.out.println(buf.position()); //0
        System.out.println(buf.limit());    //5
        System.out.println(buf.capacity()); //1024

        //6. clear() : 清空缓冲区. 但是缓冲区中的数据依然存在，但是处于“被遗忘”状态
        buf.clear();

        System.out.println("-----------------clear()----------------");
        System.out.println(buf.position()); //0
        System.out.println(buf.limit());    //1024
        System.out.println(buf.capacity()); //1024

        System.out.println((char) buf.get());   //a;内容还在，只是将索引放在 0，之后的赋值操作即 覆盖 操作
    }
}
