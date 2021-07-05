package com.ligz.im.client.command;

import com.ligz.im.protocol.request.JoinGroupRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class JoinGroupCommand implements Command {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequest joinGroupRequest = new JoinGroupRequest();
        System.out.println("please input groupId, join the group");
        String groupId = scanner.next();

        joinGroupRequest.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequest);
    }
}
