package com.ligz.netty.session;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

public interface Session {
    void bind(User user, Channel channel);

    void unBind(Channel channel);

    boolean hasLogin(Channel channel);

    User getUser(Channel channel);

    Channel getChannel(String userId);

    void bindChannelGroup(String groupId, ChannelGroup channelGroup);

    ChannelGroup getChannelGroup(String groupId);
}
