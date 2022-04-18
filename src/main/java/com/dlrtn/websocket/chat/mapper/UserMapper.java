package com.dlrtn.websocket.chat.mapper;

import com.dlrtn.websocket.chat.model.domain.User;
import com.dlrtn.websocket.chat.model.payload.PassWordUpdateRequest;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {

    void save(User user);

    void delete(User user);

    User findByUserId(String userId);

    void updateUserInfo(User user);

    void updatePassword(User user);

}
