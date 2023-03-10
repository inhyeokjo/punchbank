package com.kkb.punchbank.repository;

import com.kkb.punchbank.domain.PbUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<PbUser, Long> {
	Optional<PbUser> getPbUser_usernameByUserId(long userId);
}