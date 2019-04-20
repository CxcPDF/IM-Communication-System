package com.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;

/**
 * @Author: Dunfu Peng
 * @Date: 2019/4/19 23:19
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap
                //1. 指定线程模型
                .group(group)
                //2. 指定IO类型为NIO
                .channel(NioSocketChannel.class)
                //3. IO处理逻辑
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) {//比较奇怪这里抛出异常之后好像不能发送消息了
                        channel.pipeline().addLast(new StringEncoder());
                    }
                });
        Channel channel = bootstrap.connect("127.0.0.1", 8000).channel();
        System.out.println("连接....");

        while (true) {
            System.out.println("发送消息.....");
            channel.writeAndFlush(new Date() + ": hello world!");
            Thread.sleep(2000);
        }
    }



}
