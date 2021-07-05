package com.ligz.im.client.command;

import io.netty.channel.Channel;

import java.util.Scanner;

public interface Command {
    void exec(Scanner scanner, Channel channel);
}
