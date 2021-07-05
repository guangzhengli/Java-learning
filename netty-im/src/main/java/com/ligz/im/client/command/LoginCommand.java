package com.ligz.im.client.command;

import com.ligz.im.protocol.request.LoginRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class LoginCommand implements Command {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequest loginRequest = new LoginRequest();
        System.out.println("please input your username");
        loginRequest.setUserName(scanner.nextLine());
        loginRequest.setPassword("pwd");

        channel.writeAndFlush(loginRequest);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
        }
    }
}
