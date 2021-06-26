package com.ligz.netty.codec;

import com.ligz.netty.protocol.CustomProtocolCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class CustomProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        out.add(CustomProtocolCodec.INSTANCE.decode(msg));
    }
}
