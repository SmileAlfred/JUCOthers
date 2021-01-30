package practise;

import org.junit.Test;
import sun.java2d.pipe.SpanIterator;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static TCPClient mTcpClient1;
    private static String ipAdress = "192.168.137.1";

    public static void main(String[] args) {
        TCPServerService tcpServerService = new TCPServerService();

        mTcpClient1 = new TCPClient("客户端A");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("这里是客户端：\n1：建立连接；\n2：发送消息；\n3：断开连接");
            String input = scanner.next();
            switch (input) {
                case "1":
                    System.out.println("连接谁？");
                    String msg = scanner.next();
                    if (null != msg || msg.length() != 0)
                        ipAdress = msg;
                    mTcpClient1.requestConnectTcp(ipAdress);
                    break;
                case "2":
                    System.out.println("你想说啥？");
                    msg = scanner.next();
                    mTcpClient1.sendMsg(msg);
                    break;
                case "3":
                    mTcpClient1.disconnectTcp();
                    break;
                case "exist":
                    return;
                default:
                    break;
            }
        }
    }
}
