package com.learn.aio.client;

import com.study.info.HostInfo;
import com.study.util.InputUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

class ClientReadHandler implements CompletionHandler<Integer, ByteBuffer>{
    private AsynchronousSocketChannel clientChannel;
    private CountDownLatch latch;

    public ClientReadHandler(AsynchronousSocketChannel clientChannel, CountDownLatch latch) {
        this.clientChannel = clientChannel;
        this.latch = latch;
    }

    @Override
    public void completed(Integer result, ByteBuffer buffer) {
        buffer.flip();
        String readMsg = new String(buffer.array(), 0, buffer.remaining());
        System.out.println(readMsg);
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.latch.countDown();
    }
}
class ClientWriteHandler implements CompletionHandler<Integer, ByteBuffer>{
    private AsynchronousSocketChannel clientChannel;
    private CountDownLatch latch;

    public ClientWriteHandler(AsynchronousSocketChannel clientChannel, CountDownLatch latch) {
        this.clientChannel = clientChannel;
        this.latch = latch;
    }

    @Override
    public void completed(Integer result, ByteBuffer buffer) {
        if(buffer.hasRemaining()){
            this.clientChannel.write(buffer, buffer, this);
        }else {
            ByteBuffer readBuffer = ByteBuffer.allocate(100);
            this.clientChannel.read(readBuffer, readBuffer, new ClientReadHandler(this.clientChannel, this.latch));
        }
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.latch.countDown();
    }
}

class AIOClientThread implements Runnable {

    private AsynchronousSocketChannel clientChannel;
    private CountDownLatch latch;

    public AIOClientThread() throws Exception{
        this.clientChannel = AsynchronousSocketChannel.open();
        this.clientChannel.connect(new InetSocketAddress(HostInfo.HOST_NAME, HostInfo.PORT));
        this.latch = new CountDownLatch(1);
    }

    @Override
    public void run() {
        try {
            this.latch.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean sendMessage(String msg){
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        byteBuffer.put(msg.getBytes());
        byteBuffer.flip();
        this.clientChannel.write(byteBuffer, byteBuffer, new ClientWriteHandler(this.clientChannel, this.latch));
        if("byebye".equalsIgnoreCase(msg)){
            return false;
        }
        return  true;
    }
}

public class AIOEchoClient {
    public static void main(String[] args) throws Exception{
        AIOClientThread clientThread = new AIOClientThread();
        new Thread(clientThread).start();
        while (clientThread.sendMessage(InputUtil.getString("请输入要发送的内容："))){

        }
    }
}
