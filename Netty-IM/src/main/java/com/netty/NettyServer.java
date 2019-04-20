package com.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Netty服务端的启动流程：创建一个引导类，然后给他指定线程模型、IO模型，
 * 连接读写处理逻辑，绑定端口之后，服务端就启动起来了
 *
 * @Author: Dunfu Peng
 * @Date: 2019/4/19 23:07
 */
public class NettyServer {
    public static void main(String[] args) {
        //引导我们进行服务端的启动工作
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //监听端口，accept新连接的线程组
        NioEventLoopGroup boss = new NioEventLoopGroup();
        //处理每一条连接的数据读写的线程组
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                .group(boss, worker)//配置两大线程组
                //childHandler用于指定处理新连接数据的读写数理逻辑，handler用于指定在服务端启动过程中的一下逻辑
                .channel(NioServerSocketChannel.class)//指定IO模型
                .childHandler(new ChannelInitializer<NioSocketChannel>() {//定义后续每条连接的数据读写、业务处理逻辑
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                System.out.println(msg);
                            }
                        });
                    }
                })
                .bind(8000)//bind()是异步的方法，调用之后立即返回
                //添加一个监听器来检测端口是否绑定成功
                .addListener(new GenericFutureListener<Future<? super Void>>() {
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        if(future.isSuccess()){
                            System.out.println("端口绑定成功");
                        }else{
                            System.out.println("端口绑定失败");
                        }
                    }
                });
    }
}
