/*
package com.example.medichart.admin.service;

import com.example.medichart.UserLogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserStatisticsService {

    private final UserRepository userRepository;

    @Autowired
    public UserStatisticsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 오늘 가입한 사용자 수를 반환하는 메서드
    public long getTodaySignupCount() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay(); // 하루의 시작 시간
        LocalDateTime endOfDay = startOfDay.plusDays(1); // 하루의 끝 시간
        return userRepository.countByCreatedDateBetween(startOfDay, endOfDay); // 주어진 기간 내에 생성된 사용자 수를 반환
    }

    // 최근 7일 동안의 가입자 수를 리스트로 반환하는 메서드
    public List<Long> getLast7DaysSignupCounts() {
        return getSignupCountsForDays(7); // 7일 동안의 데이터를 반환
    }

    // 최근 12개월 동안의 가입자 수를 리스트로 반환하는 메서드
    public List<Long> getLast12MonthsSignupCounts() {
        return getSignupCountsForMonths(12); // 12개월 동안의 데이터를 반환
    }

    // 최근 3년 동안의 가입자 수를 리스트로 반환하는 메서드
    public List<Long> getLast3YearsSignupCounts() {
        return getSignupCountsForYears(3); // 3년 동안의 데이터를 반환
    }

    // 주어진 일 수 동안의 가입자 수를 리스트로 반환하는 메서드
    private List<Long> getSignupCountsForDays(int days) {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay(); // 현재 날짜
        return IntStream.range(0, days) // 0부터 days까지의 스트림 생성
                .mapToObj(i -> today.minusDays(i)) // 각 날짜를 계산
                .map(date -> {
                    LocalDateTime startOfDay = date; // 하루의 시작 시간
                    LocalDateTime endOfDay = startOfDay.plusDays(1); // 하루의 끝 시간
                    return userRepository.countByCreatedDateBetween(startOfDay, endOfDay); // 주어진 기간 동안의 가입자 수를 반환
                })
                .collect(Collectors.toList()); // 리스트로 수집
    }

    // 주어진 월 수 동안의 가입자 수를 리스트로 반환하는 메서드
    private List<Long> getSignupCountsForMonths(int months) {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay(); // 현재 날짜
        return IntStream.range(0, months) // 0부터 months까지의 스트림 생성
                .mapToObj(i -> today.minusMonths(i)) // 각 월을 계산
                .map(date -> {
                    LocalDateTime startOfMonth = date.withDayOfMonth(1); // 월의 시작 시간
                    LocalDateTime endOfMonth = startOfMonth.plusMonths(1); // 월의 끝 시간
                    return userRepository.countByCreatedDateBetween(startOfMonth, endOfMonth); // 주어진 기간 동안의 가입자 수를 반환
                })
                .collect(Collectors.toList()); // 리스트로 수집
    }

    // 주어진 연 수 동안의 가입자 수를 리스트로 반환하는 메서드
    private List<Long> getSignupCountsForYears(int years) {
        LocalDateTime today = LocalDateTime.now().toLocalDate().atStartOfDay(); // 현재 날짜
        return IntStream.range(0, years) // 0부터 years까지의 스트림 생성
                .mapToObj(i -> today.minusYears(i)) // 각 년을 계산
                .map(date -> {
                    LocalDateTime startOfYear = date.withDayOfYear(1); // 년의 시작 시간
                    LocalDateTime endOfYear = startOfYear.plusYears(1); // 년의 끝 시간
                    return userRepository.countByCreatedDateBetween(startOfYear, endOfYear); // 주어진 기간 동안의 가입자 수를 반환
                })
                .collect(Collectors.toList()); // 리스트로 수집
    }
}
*/
