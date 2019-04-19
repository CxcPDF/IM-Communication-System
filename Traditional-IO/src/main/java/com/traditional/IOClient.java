package com.traditional;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * 客户端脸上吧服务端8000端口后，每隔两秒向服务端写一个带有时间戳的“Hello World”
 *
 * @Author: Dunfu Peng
 * @Date: 2019/4/19 22:18
 */
public class IOClient {

    public static void main(String[] args) {
        new Thread(()->{
            try {
                Socket socket=new Socket("127.0.0.1",8000);
                while (true){
                    try {
                        socket.getOutputStream().write((new Date()+" : Hello World").getBytes());
                        System.out.println("消息发送成功....");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
