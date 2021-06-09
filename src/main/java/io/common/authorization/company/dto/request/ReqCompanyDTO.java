package io.common.authorization.company.dto.request;

import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.BusinessType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

public class ReqCompanyDTO {

    // create
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateCompanyDTO {

        @ApiModelProperty(value="회사명")
        @NotNull
        private String companyName;

        @ApiModelProperty(value="사업자번호")
        @NotNull
        private String businessNo;

        @ApiModelProperty(value="사업자등록증 Path")
        private String businessUrl;

        @ApiModelProperty(value="사업자종류")
        @NotNull
        private BusinessType businessType;

        @ApiModelProperty(value="대표자명")
        @NotNull
        private String ceoName;

        @ApiModelProperty(value="회사 대표번호")
        @NotNull
        private String companyTel;

        @ApiModelProperty(value="팩스번호")
        private String fax;

        @ApiModelProperty(value="주소1")
        @NotNull
        private String address1;

        @ApiModelProperty(value="주소2")
        @NotNull
        private String address2;

        @ApiModelProperty(value="우편번호")
        @NotNull
        private String postNo;

        @ApiModelProperty(value="회사 로고 이미지")
        private String companyLogo;

        @ApiModelProperty(value="회사 활성 상태")
        @NotNull
        private ActiveStatus availableStatus;

        @ApiModelProperty(value="마지막 수정 유저 ID")
        private Long mngUserId;
    }

    // update
    @Data
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateCompanyDTO extends CreateCompanyDTO {
        @ApiModelProperty(value="회사 ID", required = true)
        @NotNull
        private Long id;
    }
}
