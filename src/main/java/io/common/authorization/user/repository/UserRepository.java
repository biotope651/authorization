package io.common.authorization.user.repository;

import io.common.authorization.common.type.UserType;
import io.common.authorization.company.entity.Company;
import io.common.authorization.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 일반 유저 리스트 조회
    Page<User> findByUserType(UserType userType, Pageable pageable);
}
