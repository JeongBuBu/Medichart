package com.example.medichart.OAuth.repository;

import java.time.LocalDateTime;

import com.example.medichart.OAuth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);

    // 주어진 날짜 범위 내에서 가입자 수를 계산합니다.
    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.createdDate BETWEEN :startDate AND :endDate")
    long countByCreatedDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
