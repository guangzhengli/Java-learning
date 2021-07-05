package com.ligz.im.server.handler;

import com.ligz.im.protocol.CustomProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.ligz.im.protocol.CommandConst.CREATE_GROUP_REQUEST;
import static com.ligz.im.protocol.CommandConst.JOIN_GROUP_REQUEST;
import static com.ligz.im.protocol.CommandConst.LIST_GROUP_MEMBERS_REQUEST;
import static com.ligz.im.protocol.CommandConst.LOGOUT_REQUEST;
import static com.ligz.im.protocol.CommandConst.QUIT_GROUP_REQUEST;
import static com.ligz.im.protocol.CommandConst.SEND_GROUP_MESSAGE_REQUEST;
import static com.ligz.im.protocol.CommandConst.SEND_MESSAGE_REQUEST;

@ChannelHandler.Sharable
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
