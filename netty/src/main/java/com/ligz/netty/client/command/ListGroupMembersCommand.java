package com.ligz.netty.client.command;

import com.ligz.netty.protocol.request.ListGroupMembersRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class ListGroupMembersCommand implements Command {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        ListGroupMembersRequest listGroupMembersRequestPacket = new ListGroupMembersRequest();
        log.info("please input groupId");
        String groupId = scanner.next();

        listGroupMembersRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
