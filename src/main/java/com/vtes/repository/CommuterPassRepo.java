package com.vtes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vtes.entity.CommuterPass;

@Repository
public interface CommuterPassRepo extends JpaRepository<CommuterPass, Integer>{

	@Query("select cp from CommuterPass cp where cp.user.id= ?1")
	CommuterPass findByUserId(Integer userId);
}
