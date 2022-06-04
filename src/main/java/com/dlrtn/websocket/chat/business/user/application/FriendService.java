package com.dlrtn.websocket.chat.business.user.application;

import com.dlrtn.websocket.chat.business.user.model.domain.Friend;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.payload.ChangeFriendStateRequest;
import com.dlrtn.websocket.chat.business.user.repository.FriendRepository;
import com.dlrtn.websocket.chat.business.user.repository.InMemorySessionRepository;
import com.dlrtn.websocket.chat.common.model.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final InMemorySessionRepository sessionRepository;

    public List<User> getFriends(String sessionId) {
        User sessionUser = sessionRepository.get(sessionId);

        return friendRepository.selectAllFriends(sessionUser);
    }

    public User getFriend(String sessionId, String friendId) {
        User sessionUser = sessionRepository.get(sessionId);

        return friendRepository.selectFriend(sessionUser, friendId);
    }

    public Friend getFriendRelation(String sessionId, String friendId) {
        User sessionUser = sessionRepository.get(sessionId);

        return friendRepository.selectFriendRelation(sessionUser, friendId);
    }

    public CommonResponse addFriend(String sessionId, String friendId) {
        User sessionUser = sessionRepository.get(sessionId);
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, friendId))) {
            return CommonResponse.failWith("Exists friendship");
        }

        friendRepository.insertIntoFriendList(sessionUser, friendId);
        return CommonResponse.success();
    }

    public CommonResponse deleteFriend(String sessionId, String friendId) {
        User sessionUser = sessionRepository.get(sessionId);
        if (Objects.isNull(friendRepository.selectFriend(sessionUser, friendId))) {
            return CommonResponse.failWith("Not Exists in friend list");
        }

        friendRepository.deleteUserFromFriendList(sessionUser, friendId);
        return CommonResponse.success();
    }

    public CommonResponse changeFriendState(String sessionId, String friendId, ChangeFriendStateRequest request) {
        User sessionUser = sessionRepository.get(sessionId);
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, friendId))) {
            return CommonResponse.failWith("Not Exists in friend list");
        }

        friendRepository.updateFriendState(sessionUser, friendId, request);
        return CommonResponse.success();
    }

}
