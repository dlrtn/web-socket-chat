package com.dlrtn.websocket.chat.business.chat.application;

import com.dlrtn.websocket.chat.business.chat.model.domain.ChatRoom;
import com.dlrtn.websocket.chat.business.chat.model.domain.ChatType;
import com.dlrtn.websocket.chat.business.chat.model.payload.CreateChatRoomRequest;
import com.dlrtn.websocket.chat.business.chat.model.payload.CreateChatRoomResponse;
import com.dlrtn.websocket.chat.business.user.application.UserService;
import com.dlrtn.websocket.chat.business.user.model.UserServiceTestsConstants;
import com.dlrtn.websocket.chat.business.user.model.payload.SignInResponse;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ChatRoomServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatRoomService chatRoomService;

    private String getSessionId() {
        return Optional.of(UserServiceTestsConstants.TEST_SIGN_IN_REQUEST)
                .map(request -> userService.signIn(null, request))
                .map(SignInResponse::getSessionId)
                .orElseThrow(() -> new CommonException("Failed to get sign in session id"));
    }

    @DisplayName("채팅방 생성 기능 테스트")
    @Test
    void create_chatroom_test() {
        List<String> memberIds = Arrays.asList("1", "2", "3");

        CreateChatRoomRequest request = CreateChatRoomRequest.builder()
                .chatMembers(memberIds)
                .chatType(ChatType.PUBLIC)
                .chatRoomName("123")
                .build();

        CreateChatRoomResponse response = chatRoomService.createChatRoom(getSessionId(), request);

        ChatRoom foundChatRoom = chatRoomService.getChatRoom(getSessionId(), "1"); //todo chatId 로직 수정

        Assertions.assertAll(
                () -> Assertions.assertTrue(response.isSuccess()),
                () -> Assertions.assertEquals(request.getChatRoomName(), foundChatRoom.getChatName()));
    }

    @DisplayName("채팅방 수정 기능 테스트")
    @Test
    void change_chatroom_test() {
    }

    @DisplayName("채팅방 퇴장 기능 테스트")
    @Test
    void exit_chatroom_test() {
    }

}
