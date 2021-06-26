package com.ligz.netty.codec;

import com.ligz.netty.protocol.CustomProtocol;
import com.ligz.netty.protocol.CustomProtocolCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

@ChannelHandler.Sharable
public class CustomProtocolHandler extends MessageToMessageCodec<ByteBuf, CustomProtocol> {
    public static final CustomProtocolHandler INSTANCE = new CustomProtocolHandler();

    private CustomProtocolHandler() {}
    @Override
    protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, List<Object> out) {
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
        CustomProtocolCodec.INSTANCE.encode(byteBuf, msg);
        out.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        out.add(CustomProtocolCodec.INSTANCE.decode(msg));
    }
}
