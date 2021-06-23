package com.weimai.rsc.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Copyright (c) 2017 Choice, Inc. All Rights Reserved. Choice Proprietary and Confidential.
 *
 * @author DiZhi
 * @since 2021-06-16 14:53
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("connected!");
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        try{
            int readableBytes = byteBuf.readableBytes();
            byte[] bytes = new byte[readableBytes];
            byteBuf.readBytes(bytes);

            //System.out.println("readableBytes is{"+readableBytes+"},server received message：{"+new String(bytes, StandardCharsets.UTF_8)+"}");
            System.out.println("::"+new String(bytes));
        }catch (Exception e){
           e.printStackTrace();

        }finally {
            byteBuf.release();
        }

    }
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //System.out.println(ctx.channel().remoteAddress());
        //System.out.println(ctx.channel().localAddress());
    }
    //
    //@Override
    //public void channelRead
}