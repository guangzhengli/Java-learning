package com.ligz.im.client.command;

import com.ligz.im.protocol.request.SendGroupMessageRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class SendToGroupCommand implements Command {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("send the message to the group, please input groupId and message");

        String toGroupId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(new SendGroupMessageRequest(toGroupId, message));

    }
}
