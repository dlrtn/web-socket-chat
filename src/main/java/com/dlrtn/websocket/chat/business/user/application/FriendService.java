package com.dlrtn.websocket.chat.business.user.application;

import com.dlrtn.websocket.chat.business.user.exception.FriendNotExistsException;
import com.dlrtn.websocket.chat.business.user.model.domain.Friend;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.payload.AddFriendResponse;
import com.dlrtn.websocket.chat.business.user.model.payload.ChangeFriendStateRequest;
import com.dlrtn.websocket.chat.business.user.model.payload.ChangeFriendStateResponse;
import com.dlrtn.websocket.chat.business.user.model.payload.DeleteFriendResponse;
import com.dlrtn.websocket.chat.business.user.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    @Transactional
    public AddFriendResponse addFriend(User sessionUser, String friendId) {
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, friendId))) {
            throw new FriendNotExistsException(sessionUser.getUsername());
        }
        friendRepository.insertIntoFriendList(sessionUser, friendId);

        return AddFriendResponse.success();
    }

    @Transactional
    public DeleteFriendResponse deleteFriend(User sessionUser, String friendId) {
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, friendId))) {
            throw new FriendNotExistsException(sessionUser.getUsername());
        }
        friendRepository.deleteUserFromFriendList(sessionUser, friendId);

        return DeleteFriendResponse.success();
    }

    public User getFriend(User sessionUser, String friendId) {
        User foundFriend = friendRepository.selectFriend(sessionUser, friendId);
        if (Objects.isNull(foundFriend)) {
            throw new FriendNotExistsException(sessionUser.getUsername());
        }

        return foundFriend;
    }

    public List<User> getFriends(User sessionUser) {
        List<User> foundFriends = friendRepository.selectAllFriends(sessionUser);
        if (Objects.isNull(foundFriends)) {
            throw new FriendNotExistsException(sessionUser.getUsername());
        }

        return foundFriends;
    }

    public Friend getFriendShip(User sessionUser, String friendId) {
        Friend foundFriendShip = friendRepository.selectFriendRelation(sessionUser, friendId);
        if (Objects.isNull(foundFriendShip)) {
            throw new FriendNotExistsException(sessionUser.getUsername());
        }

        return foundFriendShip;
    }

    @Transactional
    public ChangeFriendStateResponse changeFriendState(User sessionUser, String friendId, ChangeFriendStateRequest request) {
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, friendId))) {
            throw new FriendNotExistsException(sessionUser.getUsername());
        }
        friendRepository.updateFriendState(sessionUser, friendId, request);

        return ChangeFriendStateResponse.success();
    }

    public boolean isExistInBlockList(User sessionUser, String friendId) {
        return friendRepository.existsFriendInBlockedList(sessionUser, friendId);
    }

}
