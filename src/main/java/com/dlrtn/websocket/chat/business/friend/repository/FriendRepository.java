package com.dlrtn.websocket.chat.business.friend.repository;

import com.dlrtn.websocket.chat.business.friend.model.FriendInformation;
import com.dlrtn.websocket.chat.business.friend.model.domain.Friend;
import com.dlrtn.websocket.chat.business.friend.model.payload.AddFriendRequest;
import com.dlrtn.websocket.chat.business.friend.model.payload.ChangeFriendStateRequest;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.tables.TbFriend.TB_FRIEND;
import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.tables.TbUser.TB_USER;
import static com.dlrtn.websocket.chat.util.LocalDateTimeUtils.convertLocalDateTimeToString;

@RequiredArgsConstructor
@Repository
public class FriendRepository {

    private final DSLContext dslContext;

    public void insertIntoFriendList(User user, AddFriendRequest request) {
        dslContext.insertInto(TB_FRIEND,
                        TB_FRIEND.USER_ID,
                        TB_FRIEND.FRIEND_ID,
                        TB_FRIEND.FRIEND_NAME,
                        TB_FRIEND.CREATED_AT)
                .values(user.getUsername(),
                        request.getFriendId(),
                        request.getFriendName(),
                        convertLocalDateTimeToString(request.getCreatedAt()))
                .execute();
    }

    public void deleteUserFromFriendList(User user, String friendId) {
        dslContext.delete(TB_FRIEND)
                .where(TB_FRIEND.FRIEND_ID.eq(friendId)
                        .and(TB_FRIEND.USER_ID.eq(user.getUsername())))
                .execute();
    }

    public List<FriendInformation> selectAllFriends(User user) {
        return dslContext.select(
                        TB_FRIEND.FRIEND_NAME,
                        TB_FRIEND.ISFAVORITE,
                        TB_FRIEND.ISBLOCKED
                )
                .from(TB_USER)
                .join(TB_FRIEND)
                .on(TB_FRIEND.USER_ID.eq(TB_USER.USERNAME))
                .where(TB_FRIEND.USER_ID.eq(user.getUsername()))
                .fetchInto(FriendInformation.class);
    }

    public FriendInformation selectFriend(User user, String friendId) {
        return dslContext.select(
                        TB_FRIEND.FRIEND_NAME,
                        TB_FRIEND.ISFAVORITE,
                        TB_FRIEND.ISBLOCKED
                )
                .from(TB_USER)
                .join(TB_FRIEND)
                .on(TB_FRIEND.FRIEND_ID.eq(TB_USER.USERNAME))
                .where(TB_FRIEND.FRIEND_ID.eq(friendId).and(TB_FRIEND.USER_ID.eq(user.getUsername())))
                .fetchOneInto(FriendInformation.class);
    }

    public Friend selectFriendRelation(User user, String friendId) {
        return dslContext.select(
                        TB_FRIEND.ID,
                        TB_FRIEND.USER_ID,
                        TB_FRIEND.FRIEND_ID,
                        TB_FRIEND.ISBLOCKED,
                        TB_FRIEND.ISFAVORITE,
                        TB_FRIEND.CREATED_AT
                )
                .from(TB_FRIEND)
                .where(TB_FRIEND.FRIEND_ID.eq(friendId).and(TB_USER.USERNAME.eq(user.getUsername())))
                .fetchOneInto(Friend.class);
    }

    public void updateFriendState(User user, String friendId, ChangeFriendStateRequest request) {
        dslContext.update(TB_FRIEND)
                .set(TB_FRIEND.ISFAVORITE.cast(Boolean.class), request.isFavorite())
                .set(TB_FRIEND.ISBLOCKED.cast(Boolean.class), request.isBlocked())
                .where(TB_FRIEND.FRIEND_ID.eq(friendId)
                        .and(TB_FRIEND.USER_ID.eq(user.getUsername())))
                .execute();
    }

    public boolean existsFriendInBlockedList(User user, String friendId) {
        return dslContext.select()
                .from(TB_FRIEND)
                .where(TB_FRIEND.FRIEND_ID.eq(friendId).and(TB_FRIEND.USER_ID.eq(user.getUsername())))
                .execute() == 1;
    }

}
