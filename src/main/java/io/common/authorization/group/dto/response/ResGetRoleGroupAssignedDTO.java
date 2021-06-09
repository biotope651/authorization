package io.common.authorization.group.dto.response;

import io.common.authorization.auth.entity.RoleGroupAuth;
import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.group.entity.RoleGroupJoin;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 롤 그룹에 권한 매칭시 사용할 리스트
 * - 그룹에 속해있는 권한 리스트
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetRoleGroupAssignedDTO {

    @ApiModelProperty(value="롤 그룹 권한 조인 리스트")
    private List<GetRoleGroupAssignedJoin> list = new ArrayList<>();

    public ResGetRoleGroupAssignedDTO(List<RoleGroupJoin> list) {
        this.list = list.stream().map(o -> new GetRoleGroupAssignedJoin(o)).collect(Collectors.toList());
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetRoleGroupAssignedJoin {

        @ApiModelProperty(value="롤 그룹 조인 ID")
        private Long id;

        @ApiModelProperty(value="롤 그룹 ID")
        private Long roleGroupId;

        @ApiModelProperty(value="롤 그룹 권한 ID")
        private Long roleGroupAuthId;

        @ApiModelProperty(value="롤 그룹 권한명")
        private String roleGroupAuthName;

        @ApiModelProperty(value="롤 그룹 권한 활성 상태")
        private ActiveStatus roleGroupAuthStatus;

        @ApiModelProperty(value="회사 ID")
        private Long companyId;

        @ApiModelProperty(value="생성일")
        private LocalDateTime createDt;

        @ApiModelProperty(value="수정일")
        private LocalDateTime updateDt;

        @ApiModelProperty(value="마지막 수정 유저 ID")
        private Long mngUserId;

        public GetRoleGroupAssignedJoin(RoleGroupJoin roleGroupJoin) {
            this.id =roleGroupJoin.getId();
            this.roleGroupId = roleGroupJoin.getRoleGroup() == null ? null : roleGroupJoin.getRoleGroup().getId();
            this.companyId = roleGroupJoin.getCompany() == null ? null : roleGroupJoin.getCompany().getId();
            // RoleGroupAuth 필드만 모아서 셋팅
            roleGroupAuthSetting(roleGroupJoin.getRoleGroupAuth());
        }

        private void roleGroupAuthSetting(RoleGroupAuth roleGroupAuth) {
            if(roleGroupAuth != null) {
                this.roleGroupAuthId = roleGroupAuth.getId();
                this.roleGroupAuthName = roleGroupAuth.getRoleGroupAuthName();
                this.roleGroupAuthStatus = roleGroupAuth.getRoleGroupAuthStatus();
                this.createDt = roleGroupAuth.getCreateDt();
                this.updateDt = roleGroupAuth.getUpdateDt();
                this.mngUserId = roleGroupAuth.getMngUser() == null ? null : roleGroupAuth.getMngUser().getId();
            }
        }
    }
}
