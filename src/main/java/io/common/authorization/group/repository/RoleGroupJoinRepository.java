package io.common.authorization.group.repository;

import io.common.authorization.company.entity.Company;
import io.common.authorization.group.entity.RoleGroup;
import io.common.authorization.group.entity.RoleGroupJoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleGroupJoinRepository extends JpaRepository<RoleGroupJoin,Long> {

    // 디폴트 롤 그룹 권한 매칭 용도 : 매칭 된 롤 그룹 권한 리스트
    List<RoleGroupJoin> findByCompanyIsNullAndRoleGroup(RoleGroup roleGroup);

    // 회사 롤 그룹 권한 매칭 용도 : 매칭 된 롤 그룹 권한 리스트
    List<RoleGroupJoin> findByCompanyAndRoleGroup(Company company, RoleGroup roleGroup);
}
