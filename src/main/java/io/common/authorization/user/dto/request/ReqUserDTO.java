package io.common.authorization.user.dto.request;

import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.EntryType;
import io.common.authorization.common.type.UserStatus;
import io.common.authorization.common.type.UserType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

public class ReqUserDTO {

    // create
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateUserDTO {

        @ApiModelProperty(value="롤 그룹 ID")
        private Long roleGroupId;

        @ApiModelProperty(value="로그인 ID", required = true)
        @NotNull
        private String loginId;

        @ApiModelProperty(value="비밀번호", required = true)
        @NotNull
        private String password;

        @ApiModelProperty(value="유저명", required = true)
        @NotNull
        private String name;

        @ApiModelProperty(value="이메일", required = true)
        @NotNull
        private String email;

        @ApiModelProperty(value="모바일", required = true)
        @NotNull
        private String mobile;

        @ApiModelProperty(value="연락처")
        @NotNull
        private String tel;

        @ApiModelProperty(value="사용자 상태", required = true)
        @NotNull
        private UserStatus userStatus;

        @ApiModelProperty(value="계정 상태", required = true)
        private ActiveStatus accountStatus;

        @ApiModelProperty(value="유저 Type", required = true)
        @NotNull
        private UserType userType;

        @ApiModelProperty(value="가입 방법", required = true)
        @NotNull
        private EntryType entryType;

        @ApiModelProperty(value="비고 (메모)")
        private String etc;

        @ApiModelProperty(value="마지막 수정 유저 ID")
        private Long mngUserId;
    }

    // update
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateUserDTO extends CreateUserDTO {
        @ApiModelProperty(value="유저 ID", required = true)
        @NotNull
        private Long id;
    }

    // update status
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateUserStatusDTO {
        @ApiModelProperty(value="유저 ID", required = true)
        @NotNull
        private Long id;

        @ApiModelProperty(value="사용자 상태", required = true)
        @NotNull
        private UserStatus userStatus;

        @ApiModelProperty(value="계정 상태", required = true)
        private ActiveStatus accountStatus;

        @ApiModelProperty(value="마지막 수정 유저 ID")
        private Long mngUserId;
    }
}
