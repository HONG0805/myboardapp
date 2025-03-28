package com.github.hong0805.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	User findByUserID(String userID);

	User findByUserEmailAndUserName(String userEmail, String userName);
}
