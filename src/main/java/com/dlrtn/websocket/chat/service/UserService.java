package com.dlrtn.websocket.chat.service;

import com.dlrtn.websocket.chat.mapper.UserMapper;
import com.dlrtn.websocket.chat.model.domain.User;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UserService {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");

	private final UserMapper userMapper;

	@Transactional
	public void joinUser(User user) {
		LocalDateTime now = LocalDateTime.now();
		String formattedNow = formatter.format(now);
		User createdUser = user.toBuilder()
			.updateAt(formattedNow)
			.build();
		userMapper.saveUser(createdUser);
	}
}