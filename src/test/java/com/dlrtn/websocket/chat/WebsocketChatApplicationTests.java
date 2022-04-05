package com.dlrtn.websocket.chat;

import com.dlrtn.websocket.chat.mapper.UserMapper;
import com.dlrtn.websocket.chat.model.domain.User;
import com.dlrtn.websocket.chat.service.UserService;
import lombok.Builder;
import org.apache.commons.lang3.text.CompositeFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@SpringBootTest
class WebsocketChatApplicationTests {

	@Autowired
	UserService userService;

	@Autowired
	User user;

	@Autowired
	private final UserMapper userMapper;

	@Autowired
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

	@Test
	void 마이바티스_테스트(){
		LocalDateTime now = LocalDateTime.now();
		String formattedNow = formatter.format(now);

		user.toBuilder()
				.userNo(50)
				.username("dlrtn")
				.password("1234")
				.realName("wndlrtn")
				.authRole("user")
				.appendAt(formattedNow)
				.updateAt(formattedNow)
				.build();

		userMapper.saveUser(user);



	}

}