package io.common.authorization.resource.program.dto.request;

import io.common.authorization.common.type.ActiveStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

public class ReqProgramDTO {

    // create
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateProgramDTO {

        @ApiModelProperty(value="프로그램명 (KR)", required = true)
        @NotNull
        private String programNameKr;

        @ApiModelProperty(value="프로그램명 (EN)", required = true)
        @NotNull
        private String programNameEn;

        @ApiModelProperty(value="프로그램 카테고리")
        private String programCategory;

        @ApiModelProperty(value="프로그램 활성 상태")
        @NotNull
        private ActiveStatus programStatus;

        @ApiModelProperty(value="비고 (메모)")
        private String etc;

        @ApiModelProperty(value="마지막 수정 유저 ID")
        private Long mngUserId;
    }


    // update
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateProgramDTO extends CreateProgramDTO {
        @ApiModelProperty(value="프로그램명 ID", required = true)
        @NotNull
        private Long id;
    }
}
