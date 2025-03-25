package com.github.hong0805.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
	boolean existsById(String id);

	boolean existsByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.email = :email AND u.name = :name")
	Optional<User> findByEmailAndName(@Param("email") String email, @Param("name") String name);

	@Query("SELECT u FROM User u WHERE u.id = :id AND u.name = :name AND u.email = :email")
	Optional<User> findByIdAndNameAndEmail(@Param("id") String id, @Param("name") String name,
			@Param("email") String email);

	@Query("SELECT u FROM User u WHERE u.id = :id AND u.email = :email")
	Optional<User> findByIdAndEmail(@Param("id") String id, @Param("email") String email);
}