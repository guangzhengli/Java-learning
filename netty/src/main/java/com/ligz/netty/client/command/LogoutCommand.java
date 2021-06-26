package com.ligz.netty.client.command;

import com.ligz.netty.protocol.request.LogoutRequest;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LogoutCommand implements Command {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequest logoutRequestPacket = new LogoutRequest();
        channel.writeAndFlush(logoutRequestPacket);
    }
}
