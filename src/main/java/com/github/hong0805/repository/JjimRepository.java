package com.github.hong0805.repository;

import com.github.hong0805.domain.Jjim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JjimRepository extends JpaRepository<Jjim, Long> {

	Optional<Jjim> findByBbs_BbsIDAndUser_UserId(Long bbsId, String userId);

	List<Jjim> findByUser_UserId(String userId);

	long countByBbs_BbsIDAndUser_UserId(Long bbsId, String userId);

	void deleteByBbs_BbsIDAndUser_UserId(Long bbsId, String userId);
}
