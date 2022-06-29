package com.dlrtn.websocket.chat.business.friend.api;

import com.dlrtn.websocket.chat.business.friend.application.FriendService;
import com.dlrtn.websocket.chat.business.friend.model.FriendState;
import com.dlrtn.websocket.chat.business.friend.model.payload.*;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.common.aop.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class FriendApiController {

    private final FriendService friendService;

    @GetMapping("/{userId}/friend")
    public List<FriendState> getFriendList(
            @SessionUser User sessionUser
    ) {
        return friendService.getFriends(sessionUser);
    }

    @PutMapping("/{userId}/friend")
    public AddFriendResponse addFriend(
            @SessionUser User sessionUser,
            @Valid @RequestBody AddFriendRequest request) {
        return friendService.addFriend(sessionUser, request);
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
