package com.ligz.im.client.command;

import com.ligz.im.protocol.request.ListGroupMembersRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class ListGroupMembersCommand implements Command {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        ListGroupMembersRequest listGroupMembersRequest = new ListGroupMembersRequest();
        System.out.println("please input groupId:");
        String groupId = scanner.next();

        listGroupMembersRequest.setGroupId(groupId);
        channel.writeAndFlush(listGroupMembersRequest);
    }
}
