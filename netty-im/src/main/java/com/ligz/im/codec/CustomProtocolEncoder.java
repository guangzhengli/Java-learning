package com.ligz.im.codec;

import com.ligz.im.protocol.CustomProtocol;
import com.ligz.im.protocol.CustomProtocolCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CustomProtocolEncoder extends MessageToByteEncoder<CustomProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, ByteBuf out) {
        CustomProtocolCodec.INSTANCE.encode(out, msg);
    }
}
