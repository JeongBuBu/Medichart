package com.example.medichart.inquiry_system.repository;

import com.example.medichart.inquiry_system.model.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findByUsername(String username);

    // 주어진 날짜 범위 내에서 문의 사항 수를 계산합니다.
    @Query("SELECT COUNT(i) FROM Inquiry i WHERE i.createdAt BETWEEN :startDate AND :endDate")
    long countByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Specification을 사용하여 동적 쿼리 검색 기능을 지원합니다.
    Page<Inquiry> findAll(Specification<Inquiry> spec, Pageable pageable);
}
