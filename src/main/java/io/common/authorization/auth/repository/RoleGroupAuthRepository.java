package io.common.authorization.auth.repository;

import io.common.authorization.auth.entity.RoleGroupAuth;
import io.common.authorization.company.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleGroupAuthRepository extends JpaRepository<RoleGroupAuth,Long> {
    Page<RoleGroupAuth> findByCompanyIsNull(Pageable pageable);
//    Optional<RoleGroupAuth> findByCompanyIsNullOrderByCreateDt();
    List<RoleGroupAuth> findByIdNotInAndCompany(List<Long> id,Company company);

    Page<RoleGroupAuth> findByCompany(Company company, Pageable pageable);
    Optional<RoleGroupAuth> findByCompany(Company company);
}
