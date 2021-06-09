package io.common.authorization.company.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

public class ReqCompanyUserDTO {

    // create
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateCompanyUserDTO {

        @ApiModelProperty(value="회사 ID")
        @NotNull
        private Long companyId;

        @ApiModelProperty(value="마지막 수정 유저 ID")
        @NotNull
        private Long userId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateCompanyUserDTO extends CreateCompanyUserDTO {
        @ApiModelProperty(value="회사 유저 ID", required = true)
        @NotNull
        private Long id;
    }
}
