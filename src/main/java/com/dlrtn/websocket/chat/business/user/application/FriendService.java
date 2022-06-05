package com.dlrtn.websocket.chat.business.user.application;

import com.dlrtn.websocket.chat.business.user.exception.FriendNotExistsException;
import com.dlrtn.websocket.chat.business.user.model.domain.Friend;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.payload.AddFriendResponse;
import com.dlrtn.websocket.chat.business.user.model.payload.ChangeFriendStateRequest;
import com.dlrtn.websocket.chat.business.user.model.payload.ChangeFriendStateResponse;
import com.dlrtn.websocket.chat.business.user.model.payload.DeleteFriendResponse;
import com.dlrtn.websocket.chat.business.user.repository.FriendRepository;
import com.dlrtn.websocket.chat.business.user.repository.InMemorySessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final InMemorySessionRepository sessionRepository;

    @Transactional
    public AddFriendResponse addFriend(String sessionId, String friendId) {
        User sessionUser = sessionRepository.get(sessionId);
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, friendId))) {
            throw new FriendNotExistsException();
        }

        friendRepository.insertIntoFriendList(sessionUser, friendId);
        return AddFriendResponse.success();
    }

    @Transactional
    public DeleteFriendResponse deleteFriend(String sessionId, String friendId) {
        User sessionUser = sessionRepository.get(sessionId);
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, friendId))) {
            throw new FriendNotExistsException();
        }

        friendRepository.deleteUserFromFriendList(sessionUser, friendId);
        return DeleteFriendResponse.success();
    }

    public List<User> getFriends(String sessionId) {
        User sessionUser = sessionRepository.get(sessionId);
        List<User> foundFriends = friendRepository.selectAllFriends(sessionUser);
        if (Objects.isNull(foundFriends)) {
            throw new FriendNotExistsException();
        }

        return foundFriends;
    }

    public Friend getFriendShip(String sessionId, String friendId) {
        User sessionUser = sessionRepository.get(sessionId);
        Friend foundFriendShip = friendRepository.selectFriendRelation(sessionUser, friendId);
        if (Objects.isNull(foundFriendShip)) {
            throw new FriendNotExistsException();
        }

        return foundFriendShip;
    }

    @Transactional
    public ChangeFriendStateResponse changeFriendState(String sessionId, String friendId, ChangeFriendStateRequest request) {
        User sessionUser = sessionRepository.get(sessionId);
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, friendId))) {
            throw new FriendNotExistsException();
        }

        friendRepository.updateFriendState(sessionUser, friendId, request);
        return ChangeFriendStateResponse.success();
    }

    public boolean isExistInBlockList(String sessionId, String friendId) {
        User sessionUser = sessionRepository.get(sessionId);

        return friendRepository.existFriendInBlockedList(sessionUser, friendId);
    }

}
