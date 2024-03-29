package com.dlrtn.websocket.chat.business.friend.application;

import com.dlrtn.websocket.chat.business.friend.exception.FriendAlreadyExistsException;
import com.dlrtn.websocket.chat.business.friend.exception.FriendNotFoundException;
import com.dlrtn.websocket.chat.business.friend.model.FriendState;
import com.dlrtn.websocket.chat.business.friend.model.domain.Friend;
import com.dlrtn.websocket.chat.business.friend.model.payload.*;
import com.dlrtn.websocket.chat.business.friend.repository.FriendRepository;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    @Transactional
    public AddFriendResponse addFriend(User sessionUser, AddFriendRequest request) {
        if (Objects.isNull(friendRepository.selectFriend(sessionUser.getUsername(), request.getFriendId()))) {
            throw new FriendAlreadyExistsException(sessionUser);
        }

        LocalDateTime now = LocalDateTime.now();
        Friend friend = Friend.builder()
                .friendName(request.getFriendName())
                .friendId(request.getFriendId())
                .createdAt(now)
                .build();

        friendRepository.insertIntoFriendList(sessionUser.getUsername(), friend);

        return AddFriendResponse.success();
    }

    @Transactional
    public DeleteFriendResponse deleteFriend(User sessionUser, String friendId) {
        try {
            friendRepository.deleteUserFromFriendList(sessionUser.getUsername(), friendId);
            return DeleteFriendResponse.success();
        } catch (Exception e) {
            throw new CommonException(sessionUser.getUsername(), e);
        }
    }

    public FriendState getFriendProfile(User sessionUser, String friendId) {
        FriendState foundFriend = friendRepository.selectFriend(sessionUser.getUsername(), friendId);
        if (Objects.isNull(foundFriend)) {
            throw new FriendNotFoundException(sessionUser);
        }

        return foundFriend;
    }

    public List<FriendState> getFriends(User sessionUser) {
        List<FriendState> foundFriends = friendRepository.selectAllFriends(sessionUser.getUsername());
        if (Objects.isNull(foundFriends)) {
            throw new FriendNotFoundException(sessionUser);
        }

        return foundFriends;
    }

    @Transactional
    public ChangeFriendStateResponse changeFriendState(User sessionUser, String friendId, ChangeFriendStateRequest request) {
        if (Objects.isNull(friendRepository.selectFriend(sessionUser.getUsername(), friendId))) {
            throw new FriendNotFoundException(sessionUser);
        }
        friendRepository.updateFriendState(sessionUser.getUsername(), friendId, request);

        return ChangeFriendStateResponse.success();
    }

    public boolean isExistInBlockList(User sessionUser, String friendId) {
        return friendRepository.existsFriendInBlockList(sessionUser.getUsername(), friendId);
    }

}
