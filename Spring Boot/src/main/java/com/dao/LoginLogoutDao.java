package com.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entity.LoginLogoutDetails;

public interface LoginLogoutDao extends JpaRepository<LoginLogoutDetails, Long> {

//	@Query(value = "update loginlogoutdetails set is_active=?1, logout_time=?2 where login_id=?3", nativeQuery=true)
//	void doLogout(boolean isActive, Date dateTime, long loginId);
}
