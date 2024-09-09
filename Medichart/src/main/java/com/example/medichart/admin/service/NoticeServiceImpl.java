/*
package com.example.medichart.admin.service;

import com.example.medichart.admin.entity.Notice;
import com.example.medichart.admin.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public Optional<Notice> getNoticeById(Long id) {
        return noticeRepository.findById(id);
    }

    @Override
    @Transactional
    public Notice createNotice(String title, String content) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        return noticeRepository.save(notice);
    }

    @Override
    @Transactional
    public Notice updateNotice(Long id, String title, String content) {
        Optional<Notice> existingNotice = noticeRepository.findById(id);
        if (existingNotice.isPresent()) {
            Notice notice = existingNotice.get();
            notice.setTitle(title);
            notice.setContent(content);
            return noticeRepository.save(notice);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean deleteNotice(Long id) {
        if (noticeRepository.existsById(id)) {
            noticeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<Notice> getAllNotices(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    @Override
    public Page<Notice> searchNotices(String keyword, Pageable pageable) {
        return noticeRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword, pageable);
    }
}
*/
