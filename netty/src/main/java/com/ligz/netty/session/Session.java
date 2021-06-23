package com.ligz.netty.session;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Optional;

public interface Session {
    void bind(User user, Channel channel);

    void unBind(Channel channel);

    boolean hasLogin(Channel channel);

    Optional<User> getUser(Channel channel);

    Optional<Channel> getChannel(String userId);

    void bindChannelGroup(String groupId, ChannelGroup channelGroup);

    Optional<ChannelGroup> getChannelGroup(String groupId);
}
