package io.common.authorization.resource.menu.dto.request;

import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.Depth;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

public class ReqResourceMenuDTO {

    // create
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateMenuDTO {

        @ApiModelProperty(value="상위 프로그램 ID", required = true)
        @NotNull
        private Long programId;

        @ApiModelProperty(value="메뉴명", required = true)
        @NotNull
        private String menuName;

        @ApiModelProperty(value="메뉴URL", required = true)
        private String menuUrl;

        @ApiModelProperty(value="메뉴 활성 상태", required = true)
        @NotNull
        private ActiveStatus menuStatus;

        @ApiModelProperty(value="메뉴 레벨", required = true)
        @NotNull
        private Depth menuLevel;

        @ApiModelProperty(value="순서")
        private int sort;

        @ApiModelProperty(value="비고 (메모)")
        private String etc;

        @ApiModelProperty(value="마지막 수정 유저 ID")
        private Long mngUserId;

        @ApiModelProperty(value="상위 메뉴 ID")
        private Long parentMenuId;
    }

    // update
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateMenuDTO extends ReqResourceMenuDTO.CreateMenuDTO {
        @ApiModelProperty(value="메뉴 ID", required = true)
        @NotNull
        private Long menuId;
    }
}
