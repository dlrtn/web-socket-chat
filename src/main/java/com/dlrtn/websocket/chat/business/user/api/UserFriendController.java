package com.dlrtn.websocket.chat.business.user.api;

import com.dlrtn.websocket.chat.business.user.aop.SessionId;
import com.dlrtn.websocket.chat.business.user.application.FriendService;
import com.dlrtn.websocket.chat.business.user.application.UserService;
import com.dlrtn.websocket.chat.business.user.model.UserSessionConstants;
import com.dlrtn.websocket.chat.business.user.model.payload.*;
import com.dlrtn.websocket.chat.util.CookieUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public void changeFriendBlocked(
            @SessionId String sessionId,
            @PathVariable String friendId
    ) {
        friendService.addBlockUserList(sessionId, friendId);
    }

    @PatchMapping("/{userId}/friends/{friendId}")
    public void changeFriendFavorite(
            @SessionId String sessionId,
            @PathVariable String friendId
    ) {
        friendService.addFavoriteFriendList(sessionId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void withdrawUser(
            @SessionId String sessionId,
            @PathVariable String friendId
    ) {
        friendService.deleteFriend(sessionId, friendId);
    }

}