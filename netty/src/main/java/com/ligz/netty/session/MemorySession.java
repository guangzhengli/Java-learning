package com.ligz.netty.session;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MemorySession implements Session {
    private static final MemorySession INSTANCE = new MemorySession();
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();
    private static final AttributeKey<User> USER = AttributeKey.newInstance("user");

    private MemorySession() {}

    public static MemorySession getInstance() {
        return INSTANCE;
    }

    @Override
    public void bind(User user, Channel channel) {
        userIdChannelMap.put(user.getUserId(), channel);
        channel.attr(USER).set(user);
    }

    @Override
    public void unBind(Channel channel) {
        if (getUser(channel).isPresent()) {
            User user = getUser(channel).get();
            userIdChannelMap.remove(user.getUserId());
            channel.attr(USER).set(null);
            log.info("{} sign out", user.getUserName());
        }
    }

    @Override
    public boolean hasLogin(Channel channel) {
        return getUser(channel).isPresent();
    }

    @Override
    public Optional<User> getUser(Channel channel) {
        return Optional.ofNullable(channel.attr(USER).get());
    }

    @Override
    public Optional<Channel> getChannel(String userId) {
        return Optional.ofNullable(userIdChannelMap.get(userId));
    }

    @Override
    public void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        groupIdChannelGroupMap.put(groupId, channelGroup);
    }

    @Override
    public Optional<ChannelGroup> getChannelGroup(String groupId) {
        return Optional.ofNullable(groupIdChannelGroupMap.get(groupId));
    }
}
