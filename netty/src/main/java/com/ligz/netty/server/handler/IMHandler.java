package com.ligz.netty.server.handler;

import com.ligz.netty.protocol.CustomProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.ligz.netty.protocol.Command.CREATE_GROUP_REQUEST;
import static com.ligz.netty.protocol.Command.JOIN_GROUP_REQUEST;
import static com.ligz.netty.protocol.Command.LIST_GROUP_MEMBERS_REQUEST;
import static com.ligz.netty.protocol.Command.LOGOUT_REQUEST;
import static com.ligz.netty.protocol.Command.QUIT_GROUP_REQUEST;
import static com.ligz.netty.protocol.Command.SEND_GROUP_MESSAGE_REQUEST;
import static com.ligz.netty.protocol.Command.SEND_MESSAGE_REQUEST;

public class IMHandler extends SimpleChannelInboundHandler<CustomProtocol> {
    public static final IMHandler INSTANCE = new IMHandler();
    private final Map<Byte, SimpleChannelInboundHandler<? extends CustomProtocol>> handlerMap = new HashMap<>();

    private IMHandler() {
        handlerMap.put(SEND_MESSAGE_REQUEST, SendMessageRequestHandler.INSTANCE);
        handlerMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(SEND_GROUP_MESSAGE_REQUEST, SendGroupMessageRequestHandler.INSTANCE);
        handlerMap.put(LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
        handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
