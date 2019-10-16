package com.learn.aio.server;

import com.study.info.HostInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

class EchoHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel clientChannel;
    private boolean exit;

    public EchoHandler(AsynchronousSocketChannel clientChannel) {
        this.clientChannel = clientChannel;
    }

    @Override
    public void completed(Integer result, ByteBuffer byteBuffer) {
        byteBuffer.flip();  // 读取之前先重置
        String read = new String(byteBuffer.array(), 0, byteBuffer.remaining()).trim();
        String outMsg = "【Echo】" + read;    // 回应的信息
        if ("byebye".equalsIgnoreCase(read)) {
            outMsg = "服务已断开，拜拜";
            this.exit = true;
        }
        this.echoWrite(outMsg);
    }

    private void echoWrite(String content){
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.put(content.getBytes());
        buffer.flip();
        this.clientChannel.write(buffer,buffer,new CompletionHandler<Integer,ByteBuffer>(){

            @Override
            public void completed(Integer result, ByteBuffer buf) {
                if (buf.hasRemaining()){
                    EchoHandler.this.clientChannel.write(buffer, buffer, this);
                }else {
                    if(EchoHandler.this.exit == false){
                        ByteBuffer readBuffer = ByteBuffer.allocate(100);
                        EchoHandler.this.clientChannel.read(readBuffer, readBuffer, new EchoHandler(EchoHandler.this.clientChannel));
                    }
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer buf) {
                try {
                    EchoHandler.this.clientChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer byteBuffer) {
        try {
            this.clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// 连接接收的回调处理
class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AIOServerThread> {

    @Override
    public void completed(AsynchronousSocketChannel channel, AIOServerThread aioServerThread) {
        aioServerThread.getServerChannel().accept(aioServerThread, this);   // 接收连接
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        channel.read(byteBuffer, byteBuffer, new EchoHandler(channel));
    }

    @Override
    public void failed(Throwable exc, AIOServerThread aioServerThread) {
        System.out.println("客户端连接创建失败....");
        aioServerThread.getLatch().countDown();
    }
}

//设置服务器处理线程
class AIOServerThread implements Runnable {

    private AsynchronousServerSocketChannel serverChannel = null;   //服务器通道
    private CountDownLatch latch = null;    //同步处理操作层

    public AIOServerThread() throws Exception {
        this.latch = new CountDownLatch(1);
        this.serverChannel = AsynchronousServerSocketChannel.open();    //打开服务器通道
        this.serverChannel.bind(new InetSocketAddress(HostInfo.PORT));
        System.out.println("服务已启动，监听端口为：" + HostInfo.PORT);
    }

    public AsynchronousServerSocketChannel getServerChannel() {
        return serverChannel;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    @Override
    public void run() {
        this.serverChannel.accept(this, new AcceptHandler());
        try {
            this.wait();    // 线程等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class AIOEchoServer {
    public static void main(String[] args) throws Exception{
        new Thread(new AIOServerThread()).start();
    }
}
