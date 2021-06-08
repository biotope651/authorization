package io.common.authorization.group.entity;

import io.common.authorization.common.audity.JpaEntityDateAudity;
import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.AnswerType;
import io.common.authorization.common.type.UserType;
import io.common.authorization.common.type.converter.ActiveStatusConverter;
import io.common.authorization.common.type.converter.AnswerTypeConverter;
import io.common.authorization.common.type.converter.UserTypeConverter;
import io.common.authorization.company.entity.Company;
import io.common.authorization.group.dto.request.ReqRoleGroupDTO;
import io.common.authorization.resource.program.entity.Program;
import io.common.authorization.user.entity.User;
import lombok.*;

import javax.persistence.*;

/**
 * 롤 그룹 테이블
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RoleGroup extends JpaEntityDateAudity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 롤 그룹명
    private String roleGroupName;

    // 롤 그룹 상태
    @Convert(converter = ActiveStatusConverter.class)
    private ActiveStatus roleGroupStatus;

    // 디폴트 그룹
    @Convert(converter = AnswerTypeConverter.class)
    private AnswerType isDefault;

    // 롤 유저 타입
    @Convert(converter = UserTypeConverter.class)
    private UserType roleUserType;

    // 회사 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_id")
    private Company company;

    // 프로그램
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="program_id")
    private Program program;

    // 마지막 수정 유저 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mng_user_id")
    private User mngUser;

    // RoleGroup Update 정보 셋팅
    public void updateRoleGroup(ReqRoleGroupDTO.UpdateRoleGroupDTO reqRoleGroupDTO) {
        this.roleGroupName = reqRoleGroupDTO.getRoleGroupName();
        this.roleGroupStatus = reqRoleGroupDTO.getRoleGroupStatus();
        this.isDefault = reqRoleGroupDTO.getIsDefault();
        this.roleUserType = reqRoleGroupDTO.getRoleUserType();
    }
}
