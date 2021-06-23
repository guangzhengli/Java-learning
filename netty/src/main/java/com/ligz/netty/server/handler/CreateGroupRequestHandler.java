package com.ligz.netty.server.handler;

import com.ligz.netty.config.GlobalConfig;
import com.ligz.netty.exception.UserException;
import com.ligz.netty.protocol.request.CreateGroupRequest;
import com.ligz.netty.protocol.response.CreateGroupResponse;
import com.ligz.netty.session.Session;
import com.ligz.netty.util.IDUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ChannelHandler.Sharable
@Slf4j
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequest> {
    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    private CreateGroupRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequest createGroupRequest) {
        List<String> userIds = createGroupRequest.getUserIdList();
        List<String> userNames = new ArrayList<>();

        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        Session session = GlobalConfig.getSession();
        userIds.stream()
                .map(session::getChannel)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(channel -> {
                    channelGroup.add(channel);
                    userNames.add(session.getUser(channel).orElseThrow(UserException::new)
                            .getUserName());
                });
        String groupId = IDUtil.getId();
        CreateGroupResponse createGroupResponse = new CreateGroupResponse();
        createGroupResponse.setSuccess(true);
        createGroupResponse.setGroupId(groupId);
        createGroupResponse.setUserNameList(userNames);

        channelGroup.writeAndFlush(createGroupResponse);
        log.info("group create success, groupId:{}, group member:{}", groupId, userNames);
        session.bindChannelGroup(groupId, channelGroup);
    }
}
