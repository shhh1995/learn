package com.study.client;

import com.study.info.HostInfo;
import com.study.util.InputUtil;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class BIOEchoClient {
    public static void main(String[] args) throws Exception{
        Socket client = new Socket(HostInfo.HOST_NAME, HostInfo.PORT);
        Scanner scan = new Scanner(client.getInputStream());
        scan.useDelimiter("\n");
        PrintStream out = new PrintStream(client.getOutputStream());
        boolean flag = true;
        while (flag){
            String inputData = InputUtil.getString("请输入要发送的内容：").trim();
            out.println(inputData);
            if (scan.hasNext()){
                String str = scan.next();
                System.out.println(str);
            }
            if ("byebye".equalsIgnoreCase(inputData)){
                flag = false;
            }
        }
        client.close();
    }
}
