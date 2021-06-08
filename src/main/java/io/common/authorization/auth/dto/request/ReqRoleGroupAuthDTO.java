package io.common.authorization.auth.dto.request;

import io.common.authorization.common.type.ActiveStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

public class ReqRoleGroupAuthDTO {

    // create
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateRoleGroupAuthDTO {

        @ApiModelProperty(value="롤 그룹 권한 명")
        @NotNull
        private String roleGroupAuthName;

        @ApiModelProperty(value="롤 그룹 권한 활성 상태")
        @NotNull
        private ActiveStatus roleGroupAuthStatus;

        @ApiModelProperty(value="회사 ID")
        private Long companyId;

        @ApiModelProperty(value="프로그램 ID")
        @NotNull
        private Long programId;

        @ApiModelProperty(value="마지막 수정 유저 ID")
        private Long mngUserId;
    }

    // update
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateRoleGroupAuthDTO extends CreateRoleGroupAuthDTO{
        @ApiModelProperty(value="롤 그룹 권한 ID", required = true)
        @NotNull
        private Long roleGroupAuthId;
    }
}
