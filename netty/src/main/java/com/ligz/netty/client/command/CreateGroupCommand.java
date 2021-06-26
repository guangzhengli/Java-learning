package com.ligz.netty.client.command;

import com.ligz.netty.protocol.request.CreateGroupRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Scanner;

@Slf4j
public class CreateGroupCommand implements Command {

    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequest createGroupRequestPacket = new CreateGroupRequest();
        log.info("create group, please input userId list, for example: 1,2,3");
        String userIds = scanner.next();
        createGroupRequestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        channel.writeAndFlush(createGroupRequestPacket);
    }

}
