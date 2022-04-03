package com.dlrtn.websocket.chat.mapper;

import com.dlrtn.websocket.chat.model.domain.User;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    void saveUser(User user);

    User findUserByUsername(String username);

    List<User> findAllUsers();

}
