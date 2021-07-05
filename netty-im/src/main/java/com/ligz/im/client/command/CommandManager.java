package com.ligz.im.client.command;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Slf4j
public class CommandManager implements Command {
    private static final String SEND_TO_USER =  "sendToUser";
    private static final String LOGOUT =  "logout";
    private static final String LOGIN =  "login";
    private static final String CREATE_GROUP =  "createGroup";
    private static final String JOIN_GROUP =  "joinGroup";
    private static final String QUIT_GROUP =  "quitGroup";
    private static final String LIST_GROUP_MEMBERS =  "listGroupMembers";
    private static final String SEND_TO_GROUP =  "sendToGroup";

    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandManager() {
        commandMap.put(LOGIN, new LoginCommand());
        commandMap.put(SEND_TO_USER, new SendToUserCommand());
        commandMap.put(LOGOUT, new LogoutCommand());
        commandMap.put(CREATE_GROUP, new CreateGroupCommand());
        commandMap.put(JOIN_GROUP, new JoinGroupCommand());
        commandMap.put(QUIT_GROUP, new QuitGroupCommand());
        commandMap.put(LIST_GROUP_MEMBERS, new ListGroupMembersCommand());
        commandMap.put(SEND_TO_GROUP, new SendToGroupCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        String sc = scanner.next();
        Command command = commandMap.get(sc);
        if (command != null) {
            command.exec(scanner, channel);
        } else {
            log.error("Unrecognized this command:{}, please use these command:{}", sc, commandMap.keySet());
        }
    }
}
