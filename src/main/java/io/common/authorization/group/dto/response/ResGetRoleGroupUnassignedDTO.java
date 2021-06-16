package io.common.authorization.group.dto.response;

import io.common.authorization.auth.entity.RoleGroupAuth;
import io.common.authorization.common.type.ActiveStatus;
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
 * - 그룹에 속해있지 않은 권한 리스트
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetRoleGroupUnassignedDTO {

    @ApiModelProperty(value="롤 그룹 unassigned list")
    private List<GetRoleGroupUnAssignedJoin> list = new ArrayList<>();

    public ResGetRoleGroupUnassignedDTO(List<RoleGroupAuth> list) {
        this.list = list.stream().map(o -> new GetRoleGroupUnAssignedJoin(o)).collect(Collectors.toList());
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetRoleGroupUnAssignedJoin  extends ResGetRoleGroupJoinDTO.ResRoleGroupJoinWrapperDTO {

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

        public GetRoleGroupUnAssignedJoin(RoleGroupAuth roleGroupAuth) {
            this.id = null;
            this.roleGroupId = null;
            this.companyId = roleGroupAuth.getCompany() == null ? null : roleGroupAuth.getCompany().getId();
            this.roleGroupAuthId = roleGroupAuth.getId();
            this.roleGroupAuthName = roleGroupAuth.getRoleGroupAuthName();
            this.roleGroupAuthStatus = roleGroupAuth.getRoleGroupAuthStatus();
            this.createDt = roleGroupAuth.getCreateDt();
            this.updateDt = roleGroupAuth.getUpdateDt();
            this.mngUserId = roleGroupAuth.getMngUser() == null ? null : roleGroupAuth.getMngUser().getId();
        }
    }
}
