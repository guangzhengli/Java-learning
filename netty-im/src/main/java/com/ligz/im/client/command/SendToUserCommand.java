package com.ligz.im.client.command;

import com.ligz.im.protocol.request.SendMessageRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class SendToUserCommand implements Command {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("send message, please input userId and message");

        String toUserId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new SendMessageRequest(toUserId, message));
    }
}
