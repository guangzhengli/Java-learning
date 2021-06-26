package com.ligz.netty.client.command;

import com.ligz.netty.protocol.request.JoinGroupRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class JoinGroupCommand implements Command {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequest joinGroupRequestPacket = new JoinGroupRequest();
        log.info("please input groupId, join the group");
        String groupId = scanner.next();

        joinGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
