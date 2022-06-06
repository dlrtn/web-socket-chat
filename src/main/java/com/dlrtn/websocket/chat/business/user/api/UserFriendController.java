package com.dlrtn.websocket.chat.business.user.api;

import com.dlrtn.websocket.chat.business.user.aop.SessionId;
import com.dlrtn.websocket.chat.business.user.aop.SessionUser;
import com.dlrtn.websocket.chat.business.user.application.FriendService;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.payload.AddFriendResponse;
import com.dlrtn.websocket.chat.business.user.model.payload.ChangeFriendStateRequest;
import com.dlrtn.websocket.chat.business.user.model.payload.ChangeFriendStateResponse;
import com.dlrtn.websocket.chat.business.user.model.payload.DeleteFriendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.dlrtn.websocket.chat.common.model.PagePathConstants.API;
import static com.dlrtn.websocket.chat.common.model.PagePathConstants.USER;

@Slf4j
@RestController
@RequestMapping(API + USER)
@RequiredArgsConstructor
public class UserFriendController {

    private final FriendService friendService;

    @GetMapping("/{userId}/friend")
    public List<User> getFriendList(
            @SessionUser User sessionUser
    ) {
        return friendService.getFriends(sessionUser);
    }

    @PutMapping("/{userId}/friend")
    public AddFriendResponse addFriend(
            @SessionUser User sessionUser,
            String friendId
    ) {
        return friendService.addFriend(sessionUser, friendId);
    }

    @PatchMapping("/{userId}/friends/{friendId}")
    public ChangeFriendStateResponse changeFriendState(
            @SessionUser User sessionUser,
            @PathVariable String friendId,
            @Valid @RequestBody ChangeFriendStateRequest request
    ) {
        return friendService.changeFriendState(sessionUser, friendId, request);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public DeleteFriendResponse deleteFriend(
            @SessionUser User sessionUser,
            @PathVariable String friendId
    ) {
        return friendService.deleteFriend(sessionUser, friendId);
    }

}
