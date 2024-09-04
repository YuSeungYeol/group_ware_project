package com.ware.spring.notice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ware.spring.notice.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

	Page<Notice> findBynoticeTitleContaining(String keyword, Pageable pageable);
	
	Page<Notice> findBynoticeContentContaining(String keyword, Pageable pageable);
	
	@Query(value="SELECT n FROM Notice n WHERE n.noticeTitle LIKE CONCAT('%',?1,'%') OR n.noticeContent LIKE CONCAT('%',?1,'%') ORDER BY n.noticeRegDate DESC",
		countQuery="SELECT COUNT(n) FROM Notice n WHERE n.noticeTitle LIKE CONCAT('%',?1,'%') OR n.noticeContent LIKE CONCAT('%',?1,'%') ")

	Page<Notice> findByNoticeTitleOrnoticeContentContaining(String Keyword, Pageable pageable);

	Notice findByNoticeNo(Long noticeNo);
}
