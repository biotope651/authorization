package io.common.authorization.company.dto.response;

import io.common.authorization.common.type.ActiveStatus;
import io.common.authorization.common.type.BusinessType;
import io.common.authorization.company.entity.Company;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetCompanyDTO {
    @ApiModelProperty(value="현재 페이지")
    private int page;

    @ApiModelProperty(value="페이지 사이즈")
    private int size;

    @ApiModelProperty(value="전체 페이지")
    private int totalPage;

    @ApiModelProperty(value="회사 리스트")
    private List<GetCompany> list = new ArrayList<>();

    public ResGetCompanyDTO(List<Company> list, int page, int size, int totalPage) {
        this.list = list.stream().map(o -> new GetCompany(o)).collect(Collectors.toList());
        this.page = page;
        this.size = size;
        this.totalPage = totalPage;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class GetCompany {

        @ApiModelProperty(value="회사 ID")
        private Long id;

        @ApiModelProperty(value="회사명")
        private String companyName;

        @ApiModelProperty(value="사업자번호")
        private String businessNo;

        @ApiModelProperty(value="사업자등록증 Path")
        private String businessUrl;

        @ApiModelProperty(value="사업자종류")
        private BusinessType businessType;

        @ApiModelProperty(value="사업자종류명")
        private String businessTypeName;

        @ApiModelProperty(value="대표자명")
        private String ceoName;

        @ApiModelProperty(value="회사 대표번호")
        private String companyTel;

        @ApiModelProperty(value="팩스번호")
        private String fax;

        @ApiModelProperty(value="주소1")
        private String address1;

        @ApiModelProperty(value="주소2")
        private String address2;

        @ApiModelProperty(value="우편번호")
        private String postNo;

        @ApiModelProperty(value="회사 로고 이미지")
        private String companyLogo;

        @ApiModelProperty(value="회사 활성 상태")
        private ActiveStatus availableStatus;

        @ApiModelProperty(value="생성일")
        private LocalDateTime createDt;

        public GetCompany(Company company) {
            this.id = company.getId();
            this.companyName = company.getCompanyName();
            this.businessNo = company.getBusinessNo();
            this.businessUrl = company.getBusinessUrl();
            this.businessType = company.getBusinessType();
            this.businessTypeName = company.getBusinessType().getDescription();
            this.ceoName = company.getCeoName();
            this.companyTel = company.getCompanyTel();
            this.fax = company.getFax();
            this.address1 = company.getAddress1();
            this.address2 = company.getAddress2();
            this.postNo = company.getPostNo();
            this.companyLogo = company.getCompanyLogo();
            this.availableStatus = company.getAvailableStatus();
            this.createDt = company.getCreateDt();
        }
    }
}
