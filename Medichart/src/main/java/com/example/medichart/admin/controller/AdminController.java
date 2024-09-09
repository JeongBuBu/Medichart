/*
package com.example.medichart.admin.controller;

import com.example.medichart.admin.service.UserStatisticsService;
import com.example.medichart.inquiry_system.model.Inquiry;
import com.example.medichart.inquiry_system.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final UserStatisticsService userStatisticsService;
    private final InquiryService inquiryService;

    @Autowired
    public AdminController(UserStatisticsService userStatisticsService,
                           InquiryService inquiryService) {
        this.userStatisticsService = userStatisticsService;
        this.inquiryService = inquiryService;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        long todaySignupCount = userStatisticsService.getTodaySignupCount();
        List<Long> last7DaysSignupCounts = userStatisticsService.getLast7DaysSignupCounts();
        List<Long> last12MonthsSignupCounts = userStatisticsService.getLast12MonthsSignupCounts();
        List<Long> last3YearsSignupCounts = userStatisticsService.getLast3YearsSignupCounts();

        model.addAttribute("오늘가입자수", todaySignupCount);
        model.addAttribute("일별가입자수", last7DaysSignupCounts);
        model.addAttribute("월별가입자수", last12MonthsSignupCounts);
        model.addAttribute("연도별가입자수", last3YearsSignupCounts);

        return "admin";
    }

    // 방문자 수 통계
    @GetMapping("/weekly-visitor-count")
    @ResponseBody
    public List<Long> getLast7DaysSignupCounts() {
        return userStatisticsService.getLast7DaysSignupCounts();
    }

    @GetMapping("/monthly-visitor-count")
    @ResponseBody
    public List<Long> getLast12MonthsSignupCounts() {
        return userStatisticsService.getLast12MonthsSignupCounts();
    }

    @GetMapping("/yearly-visitor-count")
    @ResponseBody
    public List<Long> getLast3YearsSignupCounts() {
        return userStatisticsService.getLast3YearsSignupCounts();
    }

    // 문의사항 수 통계
    @GetMapping("/weekly-inquiry-count")
    @ResponseBody
    public List<Long> getLast7DaysInquiryCounts() {
        return inquiryService.getLast7DaysInquiryCounts();
    }

    @GetMapping("/monthly-inquiry-count")
    @ResponseBody
    public List<Long> getLast12MonthsInquiryCounts() {
        return inquiryService.getLast12MonthsInquiryCounts();
    }

    @GetMapping("/yearly-inquiry-count")
    @ResponseBody
    public List<Long> getLast3YearsInquiryCounts() {
        return inquiryService.getLast3YearsInquiryCounts();
    }

    // 문의사항 관리(관리자페이지 모든 문의사항 조회)
    @GetMapping("/inquiries")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getAllInquiries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Inquiry> inquiryPage = search.isEmpty() ?
                inquiryService.getAllInquiries(pageable) :
                inquiryService.searchInquiries(search, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", inquiryPage.getContent());
        response.put("totalElements", inquiryPage.getTotalElements());
        response.put("totalPages", inquiryPage.getTotalPages());
        response.put("currentPage", inquiryPage.getNumber());

        return ResponseEntity.ok(response);
    }
}
*/
