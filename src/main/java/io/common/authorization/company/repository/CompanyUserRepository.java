package io.common.authorization.company.repository;

import io.common.authorization.company.entity.Company;
import io.common.authorization.company.entity.CompanyUser;
import io.common.authorization.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyUserRepository extends JpaRepository<CompanyUser, Long> {
    // 회사 소속 유저 리스트 조회
    Page<CompanyUser> findByCompany(Company company, Pageable pageable);
    // 회사 소속 유저 상세 정보 조회
    Optional<CompanyUser> findByUserAndCompanyOrderByCreateDt(User user, Company company);
}
