package com.ligz.netty.client.command;

import com.ligz.netty.protocol.request.QuitGroupRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class QuitGroupCommand implements Command {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        QuitGroupRequest quitGroupRequestPacket = new QuitGroupRequest();
        log.info("please input the groupId, quit the group");
        String groupId = scanner.next();

        quitGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
