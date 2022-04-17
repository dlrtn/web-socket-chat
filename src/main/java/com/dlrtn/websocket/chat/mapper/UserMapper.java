package com.dlrtn.websocket.chat.mapper;

import com.dlrtn.websocket.chat.model.domain.User;
import com.dlrtn.websocket.chat.model.payload.PassWordUpdateRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void save(User user);

    void delete(String userId);

    User findByUserId(String userId);

    void updateUserInfo(User user);

    void updatePassword(PassWordUpdateRequest request);

}
