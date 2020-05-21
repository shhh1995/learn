package com.study.other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NIO {

    public static void main(String[] args) {
        int port = 6666;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (Selector selector = Selector.open();
                     ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
                    serverSocketChannel.bind(new InetSocketAddress(InetAddress.getLocalHost(), port));
                    serverSocketChannel.configureBlocking(false);
                    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                    while (true) {
                        selector.select(); // 阻塞等待就绪的 Channel
                        Set<SelectionKey> selectionKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iterator = selectionKeys.iterator();
                        while (iterator.hasNext()) {
                            SelectionKey key = iterator.next();
                            try (SocketChannel channel = ((ServerSocketChannel) key.channel()).accept()) {
                                channel.write(Charset.defaultCharset().encode("老王，你好~"));
                            }
                            iterator.remove();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Socket 客户端 1（接收信息并打印）
                try (Socket cSocket = new Socket(InetAddress.getLocalHost(), port)) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
                    bufferedReader.lines().forEach(s -> System.out.println("客户端 1 打印：" + s));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Socket 客户端 2（接收信息并打印）
                try (Socket cSocket = new Socket(InetAddress.getLocalHost(), port)) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
                    bufferedReader.lines().forEach(s -> System.out.println("客户端 2 打印：" + s));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
