package com.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Author: Dunfu Peng
 * @Date: 2019/4/20 21:04
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf=(ByteBuf)msg;

        System.out.println(new Date()+": 服务端读到数据->"+byteBuf.toString(Charset.forName("utf-8")));

        //回复数据给客户端
        System.out.println(new Date()+": 服务端写出数据");
        ByteBuf out=getByteBUf(ctx);
        ctx.channel().writeAndFlush(out);
    }

    private ByteBuf getByteBUf(ChannelHandlerContext ctx){
        byte[] bytes="你好，世界@583449479@qq.com".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer=ctx.alloc().buffer();
        buffer.writeBytes(bytes);
        return buffer;
    }
}
