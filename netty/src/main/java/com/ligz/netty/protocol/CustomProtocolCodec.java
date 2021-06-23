package com.ligz.netty.protocol;

import com.ligz.netty.config.GlobalConfig;
import com.ligz.netty.protocol.request.CreateGroupRequest;
import com.ligz.netty.protocol.request.HeartBeatRequest;
import com.ligz.netty.protocol.request.JoinGroupRequest;
import com.ligz.netty.protocol.request.ListGroupMembersRequest;
import com.ligz.netty.protocol.request.LoginRequest;
import com.ligz.netty.protocol.request.LogoutRequest;
import com.ligz.netty.protocol.request.QuitGroupRequest;
import com.ligz.netty.protocol.request.SendGroupMessageRequest;
import com.ligz.netty.protocol.request.SendMessageRequest;
import com.ligz.netty.protocol.response.CreateGroupResponse;
import com.ligz.netty.protocol.response.GroupMessageResponse;
import com.ligz.netty.protocol.response.HeartBeatResponse;
import com.ligz.netty.protocol.response.JoinGroupResponse;
import com.ligz.netty.protocol.response.ListGroupMembersResponse;
import com.ligz.netty.protocol.response.LoginResponse;
import com.ligz.netty.protocol.response.LogoutResponse;
import com.ligz.netty.protocol.response.MessageResponse;
import com.ligz.netty.protocol.response.QuitGroupResponse;
import com.ligz.netty.serialize.Serialize;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.ligz.netty.protocol.Command.CREATE_GROUP_REQUEST;
import static com.ligz.netty.protocol.Command.CREATE_GROUP_RESPONSE;
import static com.ligz.netty.protocol.Command.GROUP_MESSAGE_RESPONSE;
import static com.ligz.netty.protocol.Command.HEARTBEAT_REQUEST;
import static com.ligz.netty.protocol.Command.HEARTBEAT_RESPONSE;
import static com.ligz.netty.protocol.Command.JOIN_GROUP_REQUEST;
import static com.ligz.netty.protocol.Command.JOIN_GROUP_RESPONSE;
import static com.ligz.netty.protocol.Command.LIST_GROUP_MEMBERS_REQUEST;
import static com.ligz.netty.protocol.Command.LIST_GROUP_MEMBERS_RESPONSE;
import static com.ligz.netty.protocol.Command.LOGIN_REQUEST;
import static com.ligz.netty.protocol.Command.LOGIN_RESPONSE;
import static com.ligz.netty.protocol.Command.LOGOUT_REQUEST;
import static com.ligz.netty.protocol.Command.LOGOUT_RESPONSE;
import static com.ligz.netty.protocol.Command.MESSAGE_RESPONSE;
import static com.ligz.netty.protocol.Command.QUIT_GROUP_REQUEST;
import static com.ligz.netty.protocol.Command.QUIT_GROUP_RESPONSE;
import static com.ligz.netty.protocol.Command.SEND_GROUP_MESSAGE_REQUEST;
import static com.ligz.netty.protocol.Command.SEND_MESSAGE_REQUEST;

/**
 * **********************************************************************
 *                                Protocol
 * +-------+----------+------------+----------+---------+---------------+
 * |       |          |            |          |         |               |
 * |   4   |     1    |     1      |    1     |    4    |       N       |
 * +--------------------------------------------------------------------+
 * |       |          |            |          |         |               |
 * | magic |  version | serializer | command  |  length |      body     |
 * |       |          |            |          |         |               |
 * +-------+----------+------------+----------+---------+---------------+
 * message header is 4+1+1+1+4 = 11
 * = 4 // magic number = (int) 0x12345678
 * + 1 // version
 * + 1 // serializable algorithm
 * + 1 // command
 * + 4 // body length
 * + N // body
 */
 class CustomProtocolCodec {
    public static final int MAGIC_NUMBER = 0x12345678;
    public static final CustomProtocolCodec INSTANCE = new CustomProtocolCodec();

    private final Map<Byte, Class<? extends CustomProtocol>> commandMap;
    private final Serialize serialize;

    private CustomProtocolCodec() {
        serialize = GlobalConfig.getSerialize();
        commandMap = new HashMap<>();
        commandMap.put(LOGIN_REQUEST, LoginRequest.class);
        commandMap.put(LOGIN_RESPONSE, LoginResponse.class);
        commandMap.put(SEND_MESSAGE_REQUEST, SendMessageRequest.class);
        commandMap.put(MESSAGE_RESPONSE, MessageResponse.class);
        commandMap.put(LOGOUT_REQUEST, LogoutRequest.class);
        commandMap.put(LOGOUT_RESPONSE, LogoutResponse.class);
        commandMap.put(CREATE_GROUP_REQUEST, CreateGroupRequest.class);
        commandMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponse.class);
        commandMap.put(JOIN_GROUP_REQUEST, JoinGroupRequest.class);
        commandMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponse.class);
        commandMap.put(QUIT_GROUP_REQUEST, QuitGroupRequest.class);
        commandMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponse.class);
        commandMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequest.class);
        commandMap.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponse.class);
        commandMap.put(SEND_GROUP_MESSAGE_REQUEST, SendGroupMessageRequest.class);
        commandMap.put(GROUP_MESSAGE_RESPONSE, GroupMessageResponse.class);
        commandMap.put(HEARTBEAT_REQUEST, HeartBeatRequest.class);
        commandMap.put(HEARTBEAT_RESPONSE, HeartBeatResponse.class);
    }

    public void encode(ByteBuf byteBuf, CustomProtocol customProtocol) {
        byte[] bytes = serialize.serialize(customProtocol);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(customProtocol.getVersion());
        byteBuf.writeByte(serialize.getSerializeType());
        byteBuf.writeByte(customProtocol.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public CustomProtocol decode(ByteBuf byteBuf) {
        // skip magic number and version
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);

        byte serializeType = byteBuf.readByte();
        byte command = byteBuf.readByte();

        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends CustomProtocol> type = commandMap.get(command);
        // serialize only support json now
        if (type == null || serializeType != serialize.getSerializeType()) {
            return null;
        }
        return serialize.deserialize(type, bytes);
    }

}