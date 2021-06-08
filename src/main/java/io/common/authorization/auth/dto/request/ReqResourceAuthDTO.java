package io.common.authorization.auth.dto.request;

import io.common.authorization.common.type.AnswerType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

public class ReqResourceAuthDTO {

    // create
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateResourceAuthDTO {

        @ApiModelProperty(value="리소스 메뉴 ID")
        @NotNull
        private Long resourceMenuId;

        @ApiModelProperty(value="프로그램 ID")
        @NotNull
        private Long programId;

        @ApiModelProperty(value="롤 그룹 권한 ID")
        @NotNull
        private Long roleGroupAuthId;

        @ApiModelProperty(value="조회 권한")
        @NotNull
        private AnswerType isRead;

        @ApiModelProperty(value="수정 권한")
        @NotNull
        private AnswerType isModify;

        @ApiModelProperty(value="마지막 수정 유저 ID")
        private Long mngUserId;
    }

    // update
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateResourceAuthDTO extends CreateResourceAuthDTO {
        @ApiModelProperty(value="리소스 권한 ID", required = true)
        @NotNull
        private Long resourceAuthId;
    }
}
