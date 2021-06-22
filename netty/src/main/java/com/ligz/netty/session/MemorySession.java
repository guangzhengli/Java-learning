package com.ligz.netty.session;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemorySession implements Session {
    private static final MemorySession INSTANCE = new MemorySession();
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();
    private static final AttributeKey<User> USER = AttributeKey.newInstance("user");

    private MemorySession() {}

    public MemorySession getInstance() {
        return INSTANCE;
    }

    @Override
    public void bind(User user, Channel channel) {
        userIdChannelMap.put(user.getUserId(), channel);
        channel.attr(USER).set(user);
    }

    @Override
    public void unBind(Channel channel) {
        if (hasLogin(channel)) {
            User user = getUser(channel);
            userIdChannelMap.remove(user.getUserId());
            channel.attr(USER).set(null);
            System.out.println(user + " sign out");
        }
    }

    @Override
    public boolean hasLogin(Channel channel) {
        return getUser(channel) != null;
    }

    @Override
    public User getUser(Channel channel) {
        return channel.attr(USER).get();
    }

    @Override
    public Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    @Override
    public void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        groupIdChannelGroupMap.put(groupId, channelGroup);
    }

    @Override
    public ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelGroupMap.remove(groupId);
    }
}
