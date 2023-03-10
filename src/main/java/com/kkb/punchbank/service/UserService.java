package com.kkb.punchbank.service;

import com.kkb.punchbank.domain.PbUser;
import com.kkb.punchbank.global.constant.ExceptionMessage;
import com.kkb.punchbank.global.exception.badrequest.NoUserException;
import com.kkb.punchbank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public String getUsername(long userId) {
		Optional<PbUser> usersUsernameByUserId = userRepository.getPbUser_usernameByUserId(userId);
		PbUser user = usersUsernameByUserId.orElseThrow(() -> new NoUserException(ExceptionMessage.USER_NOT_FOUND));
		return user.getName();
	}
}
