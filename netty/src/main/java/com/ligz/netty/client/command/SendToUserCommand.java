package com.ligz.netty.client.command;

import com.ligz.netty.protocol.request.SendMessageRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class SendToUserCommand implements Command {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        log.info("send message, please input userId and message");

        String toUserId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new SendMessageRequest(toUserId, message));
    }
}
