import java.util.ArrayList;

/**
 * @author SmileAlfred
 * @create 2021-01-29 13:26
 * @csdn https://blog.csdn.net/liusaisaiV1
 * @description 测试系统自带的快捷短语
 */
public class TemplatesTest {
    /**
     * 1. IDEA中代码模板所处的位置：settings - Editor - Live Templates / Postfix Completion
     * 2. 常用的模板
     */
    //模板六： prsf：可生成 private static final
    private static final Object obj = new Object();

    //变形：psf
    public static final int NUM = 1;
    //变形：psfi
    public static final int NUM2 = 2;
    //变形：psfs
    public static final String NATION = "china";


    //模板一：psvm
    public static void main(String[] args) {
        //模板二：sout
        System.out.println("hello!");
        //变形：soutp / soutm / soutv / xxx.sout
        System.out.println("args = [" + args + "]");
        System.out.println("TemplatesTest.main");

        int num1 = 10;
        System.out.println("num1 = " + num1);
        int num2 = 20;
        System.out.println("num2 = " + num2);
        System.out.println(num1);

        //模板三：fori
        String[] arr = new String[]{"Tom", "Jerry", "HanMeimei", "Lilei"};
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);

        }
        //变形：iter
        for (String s : arr) {
            System.out.println(s);
        }
        //变形：itar
        for (int i = 0; i < arr.length; i++) {
            String s = arr[i];
            System.out.println(s);

        }

        //模板四：list.for
        ArrayList list = new ArrayList();
        list.add(123);
        list.add(345);
        list.add(567);

        for (Object o : list) {

        }

        //变形：list.fori
        for (int i = 0; i < list.size(); i++) {

        }

        //变形：list.forr 倒序遍历
        for (int i = list.size() - 1; i >= 0; i--) {

        }
    }

    public void method() {
        System.out.println("TemplatesTest.method");

        ArrayList list = new ArrayList();
        list.add(123);
        list.add(345);
        list.add(567);

        //模板五：ifn
        if (list == null) {

        }
        //变形:inn
        if (list != null) {

        }

        //变形：xxx.nn /  xxx.null
        if (list == null) {

        }


        if (list != null) {

        }

    }

}
