package com.weimai.rsc.handler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import com.weimai.rsc.SendClient;
import com.weimai.rsc.msg.DBTable;
import com.weimai.rsc.msg.MessageProtocol;
import com.weimai.rsc.msg.impl.MessageServiceImpl;
import com.weimai.rsc.util.HessianUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Copyright (c) 2017 Choice, Inc. All Rights Reserved. Choice Proprietary and Confidential.
 * <p>
 * 发送消息工具类
 *
 * @author DiZhi
 * @since 2021-06-21 20:18
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private final MessageServiceImpl messageService;

    public NettyClientHandler() {
        messageService = MessageServiceImpl.getSingleInstance();
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress socketAddress = (InetSocketAddress)ctx.channel().remoteAddress();

        int port = socketAddress.getPort();
        String hostAddress = socketAddress.getAddress().getHostAddress();
        System.out.println("已链接至"+hostAddress+":"+port);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        InetSocketAddress socketAddress = (InetSocketAddress)ctx.channel().remoteAddress();
        //System.out.println(ip+":"+port+"已断开链接");
        ctx.close();
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) {
        System.out.println(HessianUtils.read(messageProtocol.getProtocolBody().getContent(),
                                             DBTable.class));
        DBTable read = HessianUtils.read(messageProtocol.getProtocolBody().getContent(), DBTable.class);

        messageService.cacheMessage(messageProtocol);
    }

}
