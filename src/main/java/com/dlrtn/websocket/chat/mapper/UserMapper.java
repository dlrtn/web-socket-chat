package com.dlrtn.websocket.chat.mapper;

import com.dlrtn.websocket.chat.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void saveUser(UserVo userVo);
}