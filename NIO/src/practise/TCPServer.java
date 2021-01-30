package practise;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TCPServer {
    private final static String TAG = TCPServer.class.getSimpleName();

    private String mSendMsg;
    private Selector mSelector;

    public void init() {
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            // 设置非阻塞
            serverSocketChannel.configureBlocking(false);
            // 获取与此Channel关联的ServerSocket并绑定端口
            serverSocketChannel.socket().bind(new InetSocketAddress(9999));
            // 注册到Selector，等待连接
            mSelector = Selector.open();
            serverSocketChannel.register(mSelector, SelectionKey.OP_ACCEPT);
            while (mSelector != null && mSelector.isOpen()) {
                // 选择一组对应Channel已准备好进行I/O的Key
                int select = mSelector.select();
                if (select <=0) {
                    continue;
                }
                // 获得Selector已选择的Keys
                Set<SelectionKey> selectionKeys = mSelector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();

                    // 移除当前的key
                    iterator.remove();

                    if (selectionKey.isValid() && selectionKey.isAcceptable()) {
                        handleAccept(selectionKey);
                    }
                    if (selectionKey.isValid() && selectionKey.isReadable()) {
                        handleRead(selectionKey);
                    }
                    if (selectionKey.isValid() && selectionKey.isWritable()) {
                        handleWrite(selectionKey);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (mSelector != null) {
                    mSelector.close();
                    mSelector = null;
                }
                if (serverSocketChannel != null) {
                    serverSocketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
        SocketChannel client = server.accept();
        client.configureBlocking(false);
        // 注册读就绪事件
        client.register(mSelector, SelectionKey.OP_READ);
       System.out.println(TAG+ "服务端 同意 客户端(" + client.getRemoteAddress() + ") 的连接请求");
    }

    private void handleRead(SelectionKey selectionKey) throws IOException {
        SocketChannel client = (SocketChannel) selectionKey.channel();

        //读取服务器发送来的数据到缓冲区中
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int bytesRead = client.read(byteBuffer);
        if (bytesRead > 0) {
            String inMsg = new String(byteBuffer.array(), 0, bytesRead);
            // 处理数据
            responseMsg(selectionKey, inMsg);
        }
        else {
           System.out.println(TAG+ "服务端 断开跟 客户端(" + client.getRemoteAddress() + ") 的连接");
            client.close();
        }
    }

    private void handleWrite(SelectionKey selectionKey) throws IOException {
        if (null == mSendMsg||mSendMsg.length() == 0) {
            return;
        }
        SocketChannel client = (SocketChannel) selectionKey.channel();

        ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
        sendBuffer.put(mSendMsg.getBytes());
        sendBuffer.flip();

        client.write(sendBuffer);
        mSendMsg = null;

        client.register(mSelector, SelectionKey.OP_READ);
    }

    /**
     * 处理数据
     *
     * @param selectionKey
     * @param inMsg
     * @throws IOException
     */
    private void responseMsg(SelectionKey selectionKey, String inMsg) throws IOException {
        SocketChannel client = (SocketChannel) selectionKey.channel();
       System.out.println(TAG+ "服务端 收到 客户端(" + client.getRemoteAddress() + ") 数据：" + inMsg);

        // 估计1亿的AI代码
        String outMsg = inMsg;
        outMsg = outMsg.replace("吗", "");
        outMsg = outMsg.replace("?", "!");
        outMsg = outMsg.replace("？", "!");
        sendMsg(selectionKey, outMsg);
    }

    /**
     * 发送数据
     *
     * @param selectionKey
     * @param msg
     * @throws IOException
     */
    public void sendMsg(SelectionKey selectionKey, String msg) throws IOException {
        mSendMsg = msg;
        SocketChannel client = (SocketChannel) selectionKey.channel();
        client.register(mSelector, SelectionKey.OP_WRITE);
       System.out.println(TAG+ "服务端 给 客户端(" + client.getRemoteAddress() + ") 发送数据：" + msg);
    }

    /**
     * 断开连接
     */
    public void close() {
        try {
           System.out.println(TAG+ "服务端中断所有连接");
            mSelector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
