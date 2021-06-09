package io.common.authorization.group.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

public class ReqRoleGroupJoinDTO {

    // create
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateRoleGroupJoinDTO {
        @ApiModelProperty(value="롤 그룹 ID")
        @NotNull
        private Long roleGroupId;

        @ApiModelProperty(value="롤 그룹 권한 ID")
        @NotNull
        private Long roleGroupAuthId;

        @ApiModelProperty(value="회사 ID")
        private Long companyId;
    }

    // delete
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DeleteRoleGroupJoinDTO {
        @ApiModelProperty(value="롤 그룹 조인 ID", required = true)
        @NotNull
        private Long id;
    }
}
