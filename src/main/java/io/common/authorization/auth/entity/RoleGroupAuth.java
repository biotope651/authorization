package io.common.authorization.auth.entity;

import io.common.authorization.auth.dto.request.ReqRoleGroupAuthDTO;
import io.common.authorization.common.audity.JpaEntityDateAudity;
import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.converter.ActiveStatusConverter;
import io.common.authorization.company.entity.Company;
import io.common.authorization.group.entity.RoleGroup;
import io.common.authorization.resource.program.entity.Program;
import io.common.authorization.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * 롤 그룹 권한 테이블
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RoleGroupAuth extends JpaEntityDateAudity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회사 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_id")
    private Company company;

    // 롤 그룹 권한 명
    private String roleGroupAuthName;

    // 롤 그룹 권한 상태
    @Convert(converter = ActiveStatusConverter.class)
    private ActiveStatus roleGroupAuthStatus;

    // 리소스 권한
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "roleGroupAuth")
    private List<ResourceAuth> resourceAuths;

    // 프로그램
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="program_id")
    private Program program;

    // 마지막 수정 유저 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mng_user_id")
    private User mngUser;

    public void updateRoleGroupAuth(ReqRoleGroupAuthDTO.UpdateRoleGroupAuthDTO updateRoleGroupAuthDTO) {
        this.roleGroupAuthName = updateRoleGroupAuthDTO.getRoleGroupAuthName();
        this.roleGroupAuthStatus = updateRoleGroupAuthDTO.getRoleGroupAuthStatus();
    }

    public void clearAndCreateResourceAuth(List<ResourceAuth> resourceAuths) {
        this.resourceAuths.clear();
        this.resourceAuths.addAll(resourceAuths);
    }
}
