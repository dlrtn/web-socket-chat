package com.dlrtn.websocket.chat.business.friend.application;

import com.dlrtn.websocket.chat.business.friend.exception.FriendNotExistsException;
import com.dlrtn.websocket.chat.business.friend.model.FriendInformation;
import com.dlrtn.websocket.chat.business.friend.model.domain.Friend;
import com.dlrtn.websocket.chat.business.friend.model.payload.*;
import com.dlrtn.websocket.chat.business.friend.repository.FriendRepository;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    @Transactional
    public AddFriendResponse addFriend(User sessionUser, AddFriendRequest request) {
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, request.getFriendId()))) {
            throw new FriendNotExistsException(sessionUser);
        }
        friendRepository.insertIntoFriendList(sessionUser, request);

        return AddFriendResponse.success();
    }

    @Transactional
    public DeleteFriendResponse deleteFriend(User sessionUser, String friendId) {
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, friendId))) {
            throw new FriendNotExistsException(sessionUser);
        }
        friendRepository.deleteUserFromFriendList(sessionUser, friendId);

        return DeleteFriendResponse.success();
    }

    public FriendInformation getFriendProfile(User sessionUser, String friendId) {
        FriendInformation foundFriend = friendRepository.selectFriend(sessionUser, friendId);
        if (Objects.isNull(foundFriend)) {
            throw new FriendNotExistsException(sessionUser);
        }

        return foundFriend;
    }

    public List<User> getFriends(User sessionUser) {
        return Optional.ofNullable(sessionUser)
                .map(friendRepository::selectAllFriends)
                .orElseThrow(() -> {
                    assert sessionUser != null : new CommonException("SessionUser no existed");
                    return new FriendNotExistsException(sessionUser);
                });
    }

    public Friend getFriendShip(User sessionUser, String friendId) {
        Friend foundFriendShip = friendRepository.selectFriendRelation(sessionUser, friendId);
        if (Objects.isNull(foundFriendShip)) {
            throw new FriendNotExistsException(sessionUser);
        }

        return foundFriendShip;
    }

    @Transactional
    public ChangeFriendStateResponse changeFriendState(User sessionUser, String friendId, ChangeFriendStateRequest request) {
        if (Objects.nonNull(friendRepository.selectFriend(sessionUser, friendId))) {
            throw new FriendNotExistsException(sessionUser);
        }
        friendRepository.updateFriendState(sessionUser, friendId, request);

        return ChangeFriendStateResponse.success();
    }

    public boolean isExistInBlockList(User sessionUser, String friendId) {
        return friendRepository.existsFriendInBlockedList(sessionUser, friendId);
    }

}
