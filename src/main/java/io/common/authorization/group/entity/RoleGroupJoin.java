package io.common.authorization.group.entity;

import io.common.authorization.auth.entity.RoleGroupAuth;
import io.common.authorization.common.audity.JpaEntityDateAudity;
import io.common.authorization.company.entity.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 롤 그룹 조인 테이블
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RoleGroupJoin extends JpaEntityDateAudity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 롤 그룹 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_group_id")
    private RoleGroup roleGroup;

    // 롤 그룹 권한 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_group_auth_id")
    private RoleGroupAuth roleGroupAuth;

    // 회사 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_id")
    private Company company;
}
