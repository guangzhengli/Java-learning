package com.ligz.netty.client.command;

import com.ligz.netty.protocol.request.LoginRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class LoginCommand implements Command {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequest loginRequestPacket = new LoginRequest();
        log.info("please input your username");
        loginRequestPacket.setUserName(scanner.nextLine());
        loginRequestPacket.setPassword("pwd");

        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
