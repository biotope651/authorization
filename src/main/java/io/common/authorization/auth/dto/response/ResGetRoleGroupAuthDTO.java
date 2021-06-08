package io.common.authorization.auth.dto.response;

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
 * RoleGroup 권한 리스트 조회
 * - RoleGroup 권한 관리시 전체 리스트를 조회하거나
 *   회사에서 생성한 RoleGroup 권한 리스트를 조회한다.
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetRoleGroupAuthDTO {
    @ApiModelProperty(value="현재 페이지")
    private int page;

    @ApiModelProperty(value="페이지 사이즈")
    private int size;

    @ApiModelProperty(value="전체 페이지")
    private int totalPage;

    @ApiModelProperty(value="롤 그룹 권한 리스트")
    private List<GetRoleGroupAuth> list = new ArrayList<>();

    public ResGetRoleGroupAuthDTO(List<RoleGroupAuth> list, int page, int size, int totalPage) {
        this.list = list.stream().map(o -> new GetRoleGroupAuth(o)).collect(Collectors.toList());
        this.page = page;
        this.size = size;
        this.totalPage = totalPage;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetRoleGroupAuth {

        @ApiModelProperty(value="롤 그룹 권한 ID")
        private Long roleGroupAuthId;

        @ApiModelProperty(value="회사 ID")
        private Long companyId;

        @ApiModelProperty(value="롤 그룹 권한 명")
        private String roleGroupAuthName;

        @ApiModelProperty(value="롤 그룹 권한 활성 상태")
        private ActiveStatus roleGroupAuthStatus;

        @ApiModelProperty(value="생성일")
        private LocalDateTime createDt;

        @ApiModelProperty(value="수정일")
        private LocalDateTime updateDt;

        @ApiModelProperty(value="프로그램 ID")
        private Long programId;

        @ApiModelProperty(value="마지막 수정 유저 ID")
        private Long mngUserId;

        public GetRoleGroupAuth(RoleGroupAuth roleGroupAuth) {
            this.roleGroupAuthId = roleGroupAuth.getId();
            this.companyId = roleGroupAuth.getCompany() == null ? null : roleGroupAuth.getCompany().getId();
            this.programId = roleGroupAuth.getProgram() == null ? null : roleGroupAuth.getProgram().getId();
            this.roleGroupAuthName = roleGroupAuth.getRoleGroupAuthName();
            this.roleGroupAuthStatus = roleGroupAuth.getRoleGroupAuthStatus();
            this.createDt = roleGroupAuth.getCreateDt();
            this.updateDt = roleGroupAuth.getUpdateDt();
            this.mngUserId = roleGroupAuth.getMngUser() == null ? null : roleGroupAuth.getMngUser().getId();
        }
    }
}
