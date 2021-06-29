package com.ligz.netty.client.command;

import com.ligz.netty.protocol.request.QuitGroupRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class QuitGroupCommand implements Command {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        QuitGroupRequest quitGroupRequest = new QuitGroupRequest();
        System.out.println("please input the groupId, quit the group");
        String groupId = scanner.next();

        quitGroupRequest.setGroupId(groupId);
        channel.writeAndFlush(quitGroupRequest);
    }
}
