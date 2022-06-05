package com.dlrtn.websocket.chat.business.user.api;

import com.dlrtn.websocket.chat.business.user.aop.SessionId;
import com.dlrtn.websocket.chat.business.user.application.FriendService;
import com.dlrtn.websocket.chat.business.user.model.payload.ChangeFriendStateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.dlrtn.websocket.chat.common.model.PagePathConstants.API;
import static com.dlrtn.websocket.chat.common.model.PagePathConstants.USER;

@Slf4j
@RestController
@RequestMapping(API + USER)
@RequiredArgsConstructor
public class UserFriendController {

    private final FriendService friendService;

    @GetMapping("/{userId}/friend")
    public void getFriendList(
            @SessionId String sessionId
    ) {
        friendService.getFriends(sessionId);
    }

    @PutMapping("/{userId}/friend")
    public void getFriend(
            @SessionId String sessionId,
            String friendId
    ) {
        friendService.addFriend(sessionId, friendId);
    }

    @PatchMapping("/{userId}/friends/{friendId}")
    public void changeFriendState(
            @SessionId String sessionId,
            @PathVariable String friendId,
            @Valid@RequestBody ChangeFriendStateRequest request
    ) {
        friendService.changeFriendState(sessionId, friendId, request);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriend(
            @SessionId String sessionId,
            @PathVariable String friendId
    ) {
        friendService.deleteFriend(sessionId, friendId);
    }

}
