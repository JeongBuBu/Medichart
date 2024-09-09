package com.example.medichart.inquiry_system.controller;

import com.example.medichart.inquiry_system.model.Inquiry;
import com.example.medichart.inquiry_system.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminInquiryController {

    @Autowired
    private InquiryService inquiryService;

    @PostMapping("/inquiries")
    public Inquiry createInquiry(@RequestBody Inquiry inquiry) {
        return inquiryService.saveInquiry(inquiry);
    }

    @GetMapping("/inquiries")
    public ResponseEntity<List<Inquiry>> getInquiriesByUsername(@RequestParam String username) {
        List<Inquiry> inquiries = inquiryService.getInquiriesByUsername(username);
        return ResponseEntity.ok(inquiries);
    }

    @DeleteMapping("/inquiries/{id}")
    public ResponseEntity<Void> deleteInquiry(@PathVariable Long id) {
        inquiryService.deleteInquiry(id);
        return ResponseEntity.noContent().build();
    }
}
