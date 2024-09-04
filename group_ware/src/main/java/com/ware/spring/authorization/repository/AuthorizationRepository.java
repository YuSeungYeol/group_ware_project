package com.ware.spring.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ware.spring.authorization.domain.Authorization;

public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

	Authorization findByAuthorNo(Long AuthorNo);
}
