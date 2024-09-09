package com.example.medichart.inquiry_system.service;

import com.example.medichart.inquiry_system.model.Inquiry;
import com.example.medichart.inquiry_system.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    @Autowired
    public InquiryService(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    public Inquiry saveInquiry(Inquiry inquiry) {
        inquiry.setCreatedAt(LocalDateTime.now());
        return inquiryRepository.save(inquiry);
    }

    public List<Inquiry> getInquiriesByUsername(String username) {
        return inquiryRepository.findByUsername(username);
    }

    public List<Long> getLast7DaysInquiryCounts() {
        return getInquiryCountsForDays(7);
    }

    public List<Long> getLast12MonthsInquiryCounts() {
        return getInquiryCountsForMonths(12);
    }

    public List<Long> getLast3YearsInquiryCounts() {
        return getInquiryCountsForYears(3);
    }

    private List<Long> getInquiryCountsForDays(int days) {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
        return IntStream.range(0, days)
                .mapToObj(i -> today.minusDays(i))
                .map(date -> {
                    LocalDateTime startOfDay = date;
                    LocalDateTime endOfDay = startOfDay.plusDays(1);
                    return inquiryRepository.countByCreatedAtBetween(startOfDay, endOfDay);
                })
                .collect(Collectors.toList());
    }

    private List<Long> getInquiryCountsForMonths(int months) {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
        return IntStream.range(0, months)
                .mapToObj(i -> today.minusMonths(i))
                .map(date -> {
                    LocalDateTime startOfMonth = date.withDayOfMonth(1);
                    LocalDateTime endOfMonth = startOfMonth.plusMonths(1);
                    return inquiryRepository.countByCreatedAtBetween(startOfMonth, endOfMonth);
                })
                .collect(Collectors.toList());
    }

    private List<Long> getInquiryCountsForYears(int years) {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay();
        return IntStream.range(0, years)
                .mapToObj(i -> today.minusYears(i))
                .map(date -> {
                    LocalDateTime startOfYear = date.withDayOfYear(1);
                    LocalDateTime endOfYear = startOfYear.plusYears(1);
                    return inquiryRepository.countByCreatedAtBetween(startOfYear, endOfYear);
                })
                .collect(Collectors.toList());
    }

    public Page<Inquiry> getAllInquiries(Pageable pageable) {
        return inquiryRepository.findAll(pageable);
    }

    public Page<Inquiry> searchInquiries(String search, Pageable pageable) {
        return inquiryRepository.findAll((Specification<Inquiry>) (root, query, criteriaBuilder) -> {
            if (search == null || search.isEmpty()) {
                return criteriaBuilder.conjunction(); // 검색어가 없을 경우 모든 데이터를 반환
            }
            // 'title'과 'content' 필드에서 검색어로 필터링
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + search.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), "%" + search.toLowerCase() + "%")
            );
        }, pageable);
    }

    public void deleteInquiry(Long id) {
        inquiryRepository.deleteById(id);
    }
}
