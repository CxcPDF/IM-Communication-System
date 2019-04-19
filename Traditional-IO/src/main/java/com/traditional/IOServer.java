package com.traditional;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 说明：
 * Server端首先创建了一个serverSocket来监听8000端口，
 * 然后创建一个线程，线程里面不断调用阻塞方法serverSocket.accept()获取新的连接，见（1）
 * 当获取到新的连接之后，给每条连接创建一个新的线程，这个线程负责从该连接中读取数据，见（2）
 * 然后读取数据是以字节流的方式，见（3）
 *
 * @Author: Dunfu Peng
 * @Date: 2019/4/19 22:06
 */
public class IOServer {

    public static void main(String[] args) throws IOException {

        final ServerSocket serverSocket = new ServerSocket(8000);

        //（1） 接收新线程连接
        new Thread(() -> {
            while (true) {
                try {
                    //（1）阻塞方法获取新的连接
                    Socket socket = serverSocket.accept();

                    //（2）每一个新的连接都创建一个线程，负责读取数据
                    new Thread(() -> {
                        try {
                            int len;
                            byte[] data = new byte[1024];
                            InputStream inputStream = socket.getInputStream();
                            //（3）按字节流方式读取数据
                            while ((len = inputStream.read(data)) != -1) {
                                System.out.println(new String(data, 0, len));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
