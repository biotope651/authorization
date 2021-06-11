package io.common.authorization.group.repository;

import io.common.authorization.company.entity.Company;
import io.common.authorization.group.entity.RoleGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleGroupRepository extends JpaRepository<RoleGroup, Long> {

    // RoleGroup List 조회 : CompanyId 가 있는 경우
    Page<RoleGroup> findByCompany(Company company, Pageable pageable);

    // RoleGroup List 조회 : CompanyId 가 없는 경우
    Page<RoleGroup> findByCompanyIsNullOrderByCreateDt(Pageable pageable);

    List<RoleGroup> findByCompanyOrderByRoleGroupName(Company company);

    List<RoleGroup> findByCompanyIsNullOrderByRoleGroupName();
}
