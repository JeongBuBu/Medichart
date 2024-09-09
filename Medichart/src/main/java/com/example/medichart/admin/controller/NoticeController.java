/*
package com.example.medichart.admin.controller;

import com.example.medichart.admin.entity.Notice;
import com.example.medichart.admin.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/notice")
@CrossOrigin(origins = "http://localhost:3000") // 프론트엔드 주소로 수정
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllNotices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notice> noticePage;
        if (search.isEmpty()) {
            noticePage = noticeService.getAllNotices(pageable);
        } else {
            noticePage = noticeService.searchNotices(search, pageable);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("content", noticePage.getContent());
        response.put("totalElements", noticePage.getTotalElements());
        response.put("totalPages", noticePage.getTotalPages());
        response.put("currentPage", noticePage.getNumber());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Notice> createNotice(@RequestBody Notice notice) {
        Notice createdNotice = noticeService.createNotice(notice.getTitle(), notice.getContent());
        return ResponseEntity.ok(createdNotice);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notice> getNoticeById(@PathVariable Long id) {
        Optional<Notice> notice = noticeService.getNoticeById(id);
        return notice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notice> updateNotice(@PathVariable Long id, @RequestBody Notice updatedNotice) {
        Notice updated = noticeService.updateNotice(id, updatedNotice.getTitle(), updatedNotice.getContent());
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }
}
*/
