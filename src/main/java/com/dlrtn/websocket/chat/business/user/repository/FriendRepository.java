package com.dlrtn.websocket.chat.business.user.repository;

import com.dlrtn.websocket.chat.business.user.model.domain.Friend;
import com.dlrtn.websocket.chat.business.user.model.domain.User;
import com.dlrtn.websocket.chat.business.user.model.payload.ChangeFriendStateRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.tables.TbFriend.TB_FRIEND;
import static com.dlrtn.websocket.chat.business.chat.model.domain.generated.study_db.tables.TbUser.TB_USER;

@RequiredArgsConstructor
@Repository
public class FriendRepository {

    private final DSLContext dslContext;

    public void insertIntoFriendList(User user, String friendId) {
        dslContext.insertInto(TB_FRIEND,
                        TB_FRIEND.USER_ID,
                        TB_FRIEND.FRIEND_ID)
                .values(user.getUsername(),
                        friendId)
                .execute();
    }

    public void deleteUserFromFriendList(User user, String friendId) {
        dslContext.delete(TB_FRIEND)
                .where(TB_FRIEND.FRIEND_ID.eq(friendId)
                        .and(TB_FRIEND.USER_ID.eq(user.getUsername())))
                .execute();
    }

    public List<User> selectAllFriends(User user) {
        return dslContext.select(
                        TB_USER.USER_NO,
                        TB_USER.USERNAME,
                        TB_USER.PASSWORD,
                        TB_USER.AUTH_ROLE,
                        TB_USER.REAL_NAME,
                        TB_USER.CREATED_AT,
                        TB_USER.UPDATED_AT
                )
                .from(TB_USER)
                .join(TB_FRIEND)
                .on(TB_FRIEND.USER_ID.eq(TB_USER.USERNAME))
                .where(TB_FRIEND.USER_ID.eq(user.getUsername()))
                .fetchInto(User.class);
    }

    public User selectFriend(User user, String friendId) {
        return dslContext.select(
                        TB_USER.USER_NO,
                        TB_USER.USERNAME,
                        TB_USER.PASSWORD,
                        TB_USER.REAL_NAME,
                        TB_USER.AUTH_ROLE,
                        TB_USER.CREATED_AT,
                        TB_USER.UPDATED_AT
                )
                .from(TB_USER)
                .join(TB_FRIEND)
                .on(TB_FRIEND.FRIEND_ID.eq(TB_USER.USERNAME))
                .where(TB_USER.USERNAME.eq(friendId))
                .fetchOneInto(User.class);
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
                .set(TB_FRIEND.ISFAVORITE.cast(Boolean.class), request.isFavorite()) //TODO tinyint to boolean
                .set(TB_FRIEND.ISBLOCKED.cast(Boolean.class), request.isBlocked())
                .where(TB_FRIEND.FRIEND_ID.eq(friendId)
                        .and(TB_FRIEND.USER_ID.eq(user.getUsername())))
                .execute();
    }

}
