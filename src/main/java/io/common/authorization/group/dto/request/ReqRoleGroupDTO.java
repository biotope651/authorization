package io.common.authorization.group.dto.request;

import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.AnswerType;
import io.common.authorization.common.type.UserType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

public class ReqRoleGroupDTO {

    // create
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateRoleGroupDTO {

        @ApiModelProperty(value="롤 그룹명")
        @NotNull
        private String roleGroupName;

        @ApiModelProperty(value="롤 그룹 상태")
        @NotNull
        private ActiveStatus roleGroupStatus;

        @ApiModelProperty(value="디폴트 그룹 Y/N")
        @NotNull
        private AnswerType isDefault;

        @ApiModelProperty(value="롤 유저 타입")
        @NotNull
        private UserType roleUserType;

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
    public static class UpdateRoleGroupDTO extends CreateRoleGroupDTO {
        @ApiModelProperty(value="회사 ID", required = true)
        @NotNull
        private Long roleGroupId;
    }
}
