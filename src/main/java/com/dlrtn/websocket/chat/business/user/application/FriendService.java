package com.dlrtn.websocket.chat.business.user.application;

import com.dlrtn.websocket.chat.business.user.model.domain.User;
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

    FriendRepository friendRepository;
    InMemorySessionRepository sessionRepository;

    public CommonResponse addFriend(String sessionId, String friendId) {
        User sessionUser = sessionRepository.get(sessionId);
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, friendId))) {
            return CommonResponse.failWith("Not Exists in friend list");
        }

        friendRepository.insertIntoFriendList(sessionUser, friendId);
        return CommonResponse.success();
    }

    public CommonResponse deleteFriend(String sessionId, String friendId) {
        User sessionUser = sessionRepository.get(sessionId);
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, friendId))) {
            return CommonResponse.failWith("Not Exists in friend list");
        }

        friendRepository.deleteUserFromFriendList(sessionUser, friendId);
        return CommonResponse.success();
    }

    public List<User> getFriends(String sessionId) {
        User sessionUser = sessionRepository.get(sessionId);

        return friendRepository.selectAllFriends(sessionUser);
    }

    public CommonResponse addFavoriteFriendList(String sessionId, String friendId) {
        User sessionUser = sessionRepository.get(sessionId);
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, friendId))) {
            return CommonResponse.failWith("Not Exists in friend list");
        }

        friendRepository.updateFriendFavorite(sessionUser, friendId);
        return CommonResponse.success();
    }

    public CommonResponse addBlockUserList(String sessionId, String friendId) {
        User sessionUser = sessionRepository.get(sessionId);
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, friendId))) {
            return CommonResponse.failWith("Not Exists in friend list");
        }

        friendRepository.updateFriendBlocked(sessionUser, friendId);
        return CommonResponse.success();
    }


}
